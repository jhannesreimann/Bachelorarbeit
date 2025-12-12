import re
import javalang
import pandas as pd
from pathlib import Path


def find_javadoc_blocks(lines):
    blocks = []
    in_block = False
    current = []
    for line in lines:
        if not in_block and line.strip().startswith('/**'):
            in_block = True
            current = [line]
            continue
        if in_block:
            current.append(line)
            if '*/' in line:
                blocks.append(''.join(current))
                in_block = False
    return blocks


def analyze_file(path):
    with open(path, 'r', encoding='utf-8', errors='ignore') as f:
        lines = f.readlines()
    # Javadoc blocks
    blocks = find_javadoc_blocks(lines)
    comment_blocks = len(blocks)
    total_comment_lines = sum(len(block.splitlines()) for block in blocks)
    avg_lines_per_block = total_comment_lines / comment_blocks if comment_blocks else 0

    # Sentence and word metrics
    sentences = []
    words = []
    tag_counts = {'author': 0, 'version': 0, 'see': 0}
    for block in blocks:
        # strip * and /**, */
        text = re.sub(r'/\*\*|\*/', '', block)
        text = re.sub(r'^\s*\*', '', text, flags=re.MULTILINE)
        # sentences: split on .!? but simple split on .
        sents = [s.strip() for s in re.split(r'[\.\!?]\s+', text) if s.strip()]
        sentences.append(len(sents))
        # words
        w = [w for w in re.findall(r"\b\w+\b", text)]
        words.append(len(w))
        # tags
        tag_counts['author'] += len(re.findall(r'@author\b', block))
        tag_counts['version'] += len(re.findall(r'@version\b', block))
        tag_counts['see'] += len(re.findall(r'@see\b', block))

    total_sentences = sum(sentences)
    avg_sentences_per_block = total_sentences / comment_blocks if comment_blocks else 0
    total_words = sum(words)
    avg_words_per_block = total_words / comment_blocks if comment_blocks else 0

    # parse for missing analysis (ignored here)
    parse_error = ''
    try:
        _ = javalang.parse.parse(''.join(lines))
    except Exception as e:
        parse_error = str(e)

    return {
        'comment_blocks': comment_blocks,
        'total_comment_lines': total_comment_lines,
        'avg_lines_per_block': round(avg_lines_per_block, 2),
        'total_sentences': total_sentences,
        'avg_sentences_per_block': round(avg_sentences_per_block, 2),
        'total_words': total_words,
        'avg_words_per_block': round(avg_words_per_block, 2),
        'author_tags': tag_counts['author'],
        'version_tags': tag_counts['version'],
        'see_tags': tag_counts['see'],
        'parse_error': parse_error
    }


def main():
    repo_root = Path(__file__).resolve().parent
    base_dirs = {
        'original_with_docs': repo_root / 'Original Java Dateien',
        'generated_speaking': repo_root / 'Generierung' / 'sprechend' / 'Generated',
        'generated_obfuscated': repo_root / 'Generierung' / 'nicht sprechend' / 'Generated',
    }
    records = []
    for label, d in base_dirs.items():
        if not d.is_dir():
            continue
        for file_path in d.iterdir():
            if file_path.is_file() and file_path.name.endswith('.java'):
                metrics = analyze_file(str(file_path))
                # kombiniere
                record = {'set': label, 'file': file_path.name}
                record.update(metrics)
                records.append(record)
    df = pd.DataFrame(records)
    csv_path = repo_root / 'javadoc_comment_metrics.csv'
    excel_path = repo_root / 'javadoc_comment_metrics.xlsx'
    df.to_csv(csv_path, index=False, encoding='utf-8')
    try:
        df.to_excel(excel_path, index=False, engine='openpyxl')
        print(f"Zusätzliche Analyse abgeschlossen. Dateien: {csv_path}, {excel_path}")
    except Exception:
        print(f"CSV-Export erfolgreich: {csv_path}. Excel-Export fehlgeschlagen – bitte openpyxl installieren.")

if __name__ == '__main__':
    main()
