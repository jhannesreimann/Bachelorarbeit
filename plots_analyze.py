import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from pathlib import Path

# CSV laden
repo_root = Path(__file__).resolve().parent
csv_path = repo_root / 'doctool_gpt_classification_results.csv'
df = pd.read_csv(csv_path)

out_dir = repo_root / 'Grafiken'
out_dir.mkdir(parents=True, exist_ok=True)

# Deutsche Kategorienamen zuweisen
categories = ['hervorragend', 'unvollkommen', 'alibi', 'fehlend', 'inhaltlich falsch']
df['Kategorie'] = categories

x = np.arange(len(categories))
width = 0.35

# === Plot 1: Richtige Klassifikation ===
fig1, ax1 = plt.subplots(figsize=(10, 6))

doc_values = df['Richtig relativ'].str.rstrip('%').astype(float)
gpt_values = df['Richtig relativ3'].str.rstrip('%').astype(float)

bar1 = ax1.bar(x - width/2, doc_values, width, label='DocTool')
bar2 = ax1.bar(x + width/2, gpt_values, width, label='GPT')

ax1.set_ylabel('Korrekt klassifiziert (%)')
ax1.set_title('Richtige Klassifikation durch DocTool vs. GPT')
ax1.set_xticks(x)
ax1.set_xticklabels(categories, rotation=15)
ax1.legend()

for bars in [bar1, bar2]:
    for bar in bars:
        height = bar.get_height()
        ax1.annotate(f'{height:.1f}%',
                     xy=(bar.get_x() + bar.get_width()/2, height),
                     xytext=(0, 3), textcoords="offset points",
                     ha='center', va='bottom')

plt.tight_layout()
plt.savefig(out_dir / 'classification_accuracy.svg', format='svg')


# === Plot 2: Alibi-Scores (ohne "fehlend") ===
mask_alibi = df['Kategorie'] != 'fehlend'
df_alibi = df[mask_alibi].reset_index(drop=True)
x2 = np.arange(len(df_alibi))

fig2, ax2 = plt.subplots(figsize=(10, 6))

doc_alibi = df_alibi['Alibi-Score'].replace('/', np.nan).astype(float)
gpt_alibi = df_alibi['Alibi-Score4'].replace('/', np.nan).astype(float)

bar3 = ax2.bar(x2 - width/2, doc_alibi, width, label='DocTool')
bar4 = ax2.bar(x2 + width/2, gpt_alibi, width, label='GPT')

ax2.set_ylabel('Alibi-Score')
ax2.set_title('Alibi-Erkennung durch DocTool vs. GPT')
ax2.set_xticks(x2)
ax2.set_xticklabels(df_alibi['Kategorie'], rotation=15)
ax2.legend()

for bars in [bar3, bar4]:
    for bar in bars:
        height = bar.get_height()
        if not np.isnan(height):
            ax2.annotate(f'{height:.2f}',
                         xy=(bar.get_x() + bar.get_width()/2, height),
                         xytext=(0, 3), textcoords="offset points",
                         ha='center', va='bottom')

plt.tight_layout()
plt.savefig(out_dir / 'alibi_score_comparison.svg', format='svg')


# === Plot 3: Besser bei gleichem Ergebnis (ohne "fehlend") ===
mask_better = df['Kategorie'] != 'fehlend'
df_better = df[mask_better].reset_index(drop=True)
x3 = np.arange(len(df_better))

fig3, ax3 = plt.subplots(figsize=(10, 6))

doc_better = df_better['Besser bei gleichem Ergebnis relativ'].str.rstrip('%').replace('/', np.nan).astype(float)
gpt_better = df_better['Besser bei gleichem Ergebnis relativ7'].str.rstrip('%').replace('/', np.nan).astype(float)

bar5 = ax3.bar(x3 - width/2, doc_better, width, label='DocTool')
bar6 = ax3.bar(x3 + width/2, gpt_better, width, label='GPT')

ax3.set_ylabel('Besser bei gleicher Vorhersage (%)')
ax3.set_title('Wer ist besser bei gleichen Ergebnissen?')
ax3.set_xticks(x3)
ax3.set_xticklabels(df_better['Kategorie'], rotation=15)
ax3.legend()

for bars in [bar5, bar6]:
    for bar in bars:
        height = bar.get_height()
        if not np.isnan(height):
            ax3.annotate(f'{height:.1f}%',
                         xy=(bar.get_x() + bar.get_width()/2, height),
                         xytext=(0, 3), textcoords="offset points",
                         ha='center', va='bottom')

plt.tight_layout()
plt.savefig(out_dir / 'better_when_equal.svg', format='svg')

print("Plots exportiert als:\n- classification_accuracy.svg\n- alibi_score_comparison.svg\n- better_when_equal.svg")
plt.show()
