import os
import re
import javalang
import pandas as pd


def count_javadoc_lines(lines):
    count = 0
    in_comment = False
    for line in lines:
        if not in_comment and line.strip().startswith('/**'):
            in_comment = True
            count += 1
            continue
        if in_comment:
            count += 1
            if '*/' in line:
                in_comment = False
    return count


def extract_comment(lines, start_line):
    """
    Extracts the Javadoc block immediately preceding a declaration, skipping annotations.
    """
    idx = start_line - 2
    end = None
    for i in range(idx, -1, -1):
        stripped = lines[i].strip()
        if stripped.startswith('*/') or '*/' in stripped:
            end = i
            break
        if stripped.startswith('@') or not stripped:
            continue
        if not stripped.startswith('*') and not stripped.startswith('/**'):
            return ''
    if end is None:
        return ''
    start = None
    for i in range(end, -1, -1):
        if lines[i].strip().startswith('/**'):
            start = i
            break
    if start is None:
        return ''
    return '\n'.join(lines[start:end+1])


def analyze_file(path):
    with open(path, 'r', encoding='utf-8', errors='ignore') as f:
        lines = f.readlines()
    java = ''.join(lines)
    javadoc_lines = count_javadoc_lines(lines)
    params_missing = returns_missing = throws_missing = 0
    parse_error = ''
    try:
        tree = javalang.parse.parse(java)
    except javalang.parser.JavaSyntaxError as e:
        parse_error = str(e)
        return javadoc_lines, params_missing, returns_missing, throws_missing, parse_error
    for _, node in tree.filter(javalang.tree.MethodDeclaration):
        params = node.parameters
        thrown = node.throws or []
        comment = extract_comment(lines, node.position.line) if node.position else ''
        p_tags = len(re.findall(r'@param\s+\w+', comment))
        params_missing += max(0, len(params) - p_tags)
        if node.return_type and getattr(node.return_type, 'name', None) != 'void':
            if not re.search(r'@return', comment):
                returns_missing += 1
        t_tags = len(re.findall(r'@throws\s+\w+', comment))
        throws_missing += max(0, len(thrown) - t_tags)
    for _, node in tree.filter(javalang.tree.ConstructorDeclaration):
        params = node.parameters
        thrown = node.throws or []
        comment = extract_comment(lines, node.position.line) if node.position else ''
        p_tags = len(re.findall(r'@param\s+\w+', comment))
        params_missing += max(0, len(params) - p_tags)
        t_tags = len(re.findall(r'@throws\s+\w+', comment))
        throws_missing += max(0, len(thrown) - t_tags)
    return javadoc_lines, params_missing, returns_missing, throws_missing, parse_error


def main():
    # Nur relevante Sets: Original mit Docs und generierte Versionen
    base_dirs = {
        'original_with_docs': os.path.join('Original Java Dateien'),
        'generated_speaking': os.path.join('Generierung', 'sprechend', 'Generated'),
        'generated_obfuscated': os.path.join('Generierung', 'nicht sprechend', 'Generated'),
    }
    records = []
    for label, d in base_dirs.items():
        if not os.path.isdir(d):
            continue
        for file in os.listdir(d):
            if file.endswith('.java'):
                path = os.path.join(d, file)
                jdl, pm, rm, tm, err = analyze_file(path)
                records.append({
                    'set': label,
                    'file': file,
                    'javadoc_lines': jdl,
                    'params_missing': pm,
                    'returns_missing': rm,
                    'throws_missing': tm,
                    'parse_error': err
                })
    df = pd.DataFrame(records)
    csv_path = 'javadoc_analysis.csv'
    excel_path = 'javadoc_analysis.xlsx'
    df.to_csv(csv_path, index=False, encoding='utf-8')
    try:
        df.to_excel(excel_path, index=False, engine='openpyxl')
        print(f"Analyse abgeschlossen. Dateien: {csv_path}, {excel_path}")
    except Exception:
        print(f"CSV-Export erfolgreich: {csv_path}. Excel-Export fehlgeschlagen – bitte openpyxl installieren.")

if __name__ == '__main__':
    main()
