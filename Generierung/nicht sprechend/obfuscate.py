import os
import javalang
import random
import string
import re

def generate_random_name(length=6):
    return ''.join(random.choices(string.ascii_letters, k=length))

def replace_whole_word(text, word, replacement):
    return re.sub(r'\b' + re.escape(word) + r'\b', replacement, text)

def process_java_file(filepath, name_map):
    with open(filepath, 'r', encoding='utf-8') as f:
        code = f.read()

    try:
        tree = javalang.parse.parse(code)
    except javalang.parser.JavaSyntaxError as e:
        print(f"Syntaxfehler in Datei {filepath}: {e}")
        print(f"Position: Zeile {e.at.position[0]} Spalte {e.at.position[1]}")
        print("Fehlerzeile:")
        lines = code.splitlines()
        if 0 < e.at.position[0] <= len(lines):
            print(lines[e.at.position[0]-1])
        return


    # Namen sammeln, außer Klassennamen
    for path, node in tree:
        if isinstance(node, javalang.tree.MethodDeclaration):
            name_map.setdefault(node.name, generate_random_name())
        elif isinstance(node, javalang.tree.VariableDeclarator):
            name_map.setdefault(node.name, generate_random_name())
        elif isinstance(node, javalang.tree.FormalParameter):
            name_map.setdefault(node.name, generate_random_name())
        # Klassennamen bewusst überspringen!

    # Ersetzungen im Code
    obfuscated_code = code
    for original_name, new_name in name_map.items():
        obfuscated_code = replace_whole_word(obfuscated_code, original_name, new_name)

    # In neue Datei schreiben
    outpath = filepath.replace('.java', '_obf.java')
    with open(outpath, 'w', encoding='utf-8') as f:
        f.write(obfuscated_code)

    print(f"Obfuskiert: {filepath} → {outpath}")

def find_java_files(root_dir):
    java_files = []
    for dirpath, _, filenames in os.walk(root_dir):
        for filename in filenames:
            if filename.endswith('.java'):
                java_files.append(os.path.join(dirpath, filename))
    return java_files

if __name__ == '__main__':
    name_map = {}
    java_files = find_java_files('.')

    if not java_files:
        print("Keine Java-Dateien gefunden.")
    else:
        for java_file in java_files:
            process_java_file(java_file, name_map)

    print("Obfuskation abgeschlossen.")
