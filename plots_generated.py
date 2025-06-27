import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# CSV laden
csv_path = 'javadoc_comment_metrics.csv'
df = pd.read_csv(csv_path)

# Kategorien
sets = ['original_with_docs', 'generated_speaking', 'generated_obfuscated']
custom_labels = ['original', 'generiert normal', 'generiert verschleiert']

# Metriken für Plot 1
metrics_1 = ['avg_lines_per_block', 'avg_sentences_per_block', 'avg_words_per_block']
x1 = np.arange(len(metrics_1))
width = 0.25

grouped_1 = df.groupby('set')[metrics_1].mean().reindex(sets).reset_index()
values_1 = [grouped_1.loc[i, metrics_1].values for i in range(len(sets))]

# Metriken für Plot 2
metrics_2 = ['logically wrong', 'logically wrong per block']
x2 = np.arange(len(metrics_2))

grouped_2 = df.groupby('set')[metrics_2].mean().reindex(sets).reset_index()
values_2 = [grouped_2.loc[i, metrics_2].values for i in range(len(sets))]

# Plot-Erstellung
fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(14, 6))

# Plot 1: Kommentarstruktur
bars_1 = []
for i, val in enumerate(values_1):
    bar = ax1.bar(x1 + i*width, val, width=width, label=custom_labels[i])
    bars_1.append(bar)

ax1.set_xticks(x1 + width)
ax1.set_xticklabels(['Ø Zeilen/Block', 'Ø Sätze/Block', 'Ø Wörter/Block'])
ax1.set_ylabel('Durchschnittswerte')
ax1.set_title('Javadoc Kommentarstruktur')
ax1.legend()

# Werte beschriften
for bar_group in bars_1:
    for bar in bar_group:
        height = bar.get_height()
        ax1.annotate(f'{height:.2f}',
                     xy=(bar.get_x() + bar.get_width()/2, height),
                     xytext=(0, 3), textcoords="offset points",
                     ha='center', va='bottom')

# Plot 2: Logische Fehler
bars_2 = []
for i, val in enumerate(values_2):
    bar = ax2.bar(x2 + i*width, val, width=width, label=custom_labels[i])
    bars_2.append(bar)

# Neue X-Achsentitel für Plot 2
ax2.set_xticks(x2 + width)
ax2.set_xticklabels(['Ø logisch falsch (pro Datei)', 'Ø logisch falsch / Block'])
ax2.set_ylabel('Fehleranzahl / Rate')
ax2.set_title('Logische Fehler in Kommentaren')
ax2.legend()

# Hinweis unter Plot 2 mit Rahmen
ax2.text(0.5, -0.15,
         'Hinweis: Alle Werte stellen Durchschnittswerte pro Datei dar, nicht aufsummierte Gesamtwerte.',
         transform=ax2.transAxes,
         ha='center', va='top', fontsize=9, color='black',
         bbox=dict(boxstyle='round,pad=0.5', edgecolor='gray', facecolor='#f0f0f0'))


# Werte beschriften
for bar_group in bars_2:
    for bar in bar_group:
        height = bar.get_height()
        ax2.annotate(f'{height:.2f}',
                     xy=(bar.get_x() + bar.get_width()/2, height),
                     xytext=(0, 3), textcoords="offset points",
                     ha='center', va='bottom')

plt.tight_layout()

# SVG speichern
svg_path = 'javadoc_metrics_extended.svg'
plt.savefig(svg_path, format='svg')
plt.show()

print(f'Erweiterter Plot exportiert als: {svg_path}')
