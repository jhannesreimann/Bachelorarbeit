# Datensatz & Analyse zur Bachelorarbeit: Möglichkeiten und Grenzen von KI-Anwendungen bei der Erstellung und Analyse von externen Software-Dokumentationen

Dieses Repository enthält alle zugehörigen Daten, Skripte und Ergebnisse meiner Bachelorarbeit, die an der Universität Potsdam im Fachbereich Software Engineering verfasst wurde.

Die Arbeit untersucht die Leistungsfähigkeit von modernen KI-Modellen (Large Language Models, LLMs) im Vergleich zum etablierten, regelbasierten Werkzeug "DocTool" bei zwei zentralen Aufgaben:
1.  **Analyse:** Die Bewertung der Qualität bestehender Javadoc-Kommentare und die zuverlässige Identifikation von "Alibi-Dokumentationen".
2.  **Generierung:** Die automatische Erstellung von qualitativ hochwertigen Javadoc-Kommentaren für unkommentierten Java-Code.

## Zusammenfassung der Arbeit

> Diese Bachelorarbeit untersucht die Potenziale von KI-Anwendungen zur Verbesserung von Software-Dokumentationen, insbesondere zur Erkennung von „Alibi-Dokumentationen“ und zur automatischen Generierung. Dabei werden die Fähigkeiten eines Large Language Models (LLM) mit dem bestehenden, regelbasierten DocTool verglichen.
>
> Im ersten Teil wird die Fähigkeit des LLMs OpenAI o4 mini evaluiert, die Qualität bestehender Javadoc-Kommentare zu bewerten. Die Ergebnisse zeigen, dass das LLM mit einer Genauigkeit von 63,1 % dem rein strukturbasierten DocTool (52,5 %) durch sein semantisches Verständnis überlegen ist, insbesondere bei der Identifikation inhaltlich schwacher Kommentare.
>
> Das zweite Experiment befasste sich mit der automatischen Generierung von Javadoc. Das LLM erzeugte zwar durchweg ausführlichere und inhaltlich substanziellere Kommentare als die manuell erstellten Originale. Jedoch zeigten die generierten Dokumentationen eine höhere Fehlerrate: Im Durchschnitt enthielten sie 0,45 logische Fehler pro Datei, verglichen mit nur 0,18 in den von Menschen verfassten Originalen. Bei obfuskiertem Code stieg diese Fehlerrate nochmals signifikant an, was die Grenzen der KI bei fehlendem Kontext aufzeigt.
>
> Die Arbeit schlussfolgert, dass der größte Nutzen in einem hybriden Ansatz liegt: die Kombination der formalen, strukturellen Prüfungen von Werkzeugen wie dem DocTool mit der semantischen Analyse- und Generierungsfähigkeit von LLMs. Dieser Ansatz verspricht eine signifikante Steigerung der Effizienz und Qualität in Dokumentationsprozessen.

## Struktur des Repositories

Das Repository ist wie folgt strukturiert, um die verschiedenen Artefakte der Untersuchung zu organisieren:

### Hauptverzeichnis (`/`)

Hier befinden sich die zentralen Analyse-Skripte und die aggregierten Ergebnisdateien.

-   **`Auswertung.xlsx`**: **Die zentrale, manuelle Auswertungsdatei der gesamten Untersuchung.** Da die finale Bewertung der Werkzeug-Leistung manuell erfolgte, enthält diese Datei die "Ground Truth". Sie umfasst mehrere Arbeitsblätter:
    -   Eine detaillierte, manuelle Überprüfung der Klassifikationen von DocTool und OpenAI o4 mini für das Analyse-Experiment.
    -   Die Arbeitsmappe **"Statistiken Generierung"**, in der die quantitativen und qualitativen Ergebnisse der Dokumentationsgenerierung zusammengefasst und ausgewertet wurden.
-   `analyze_generated.py`: Python-Skript zur Analyse der formalen Vollständigkeit der generierten Javadoc-Kommentare.
-   `metrics_generated.py`: Python-Skript zur Erfassung sprachlicher Metriken (Zeilen, Sätze, Wörter) aus den Javadoc-Kommentaren.
-   `plots_analyze.py` & `plots_generated.py`: Skripte zur Erstellung der in der Arbeit verwendeten Grafiken.
-   `doctool.jar`: Ausführbare JAR-Datei des regelbasierten Werkzeugs DocTool.
-   `doctool_gpt_classification_results.csv`: Die zentralen Ergebnisse der Klassifikationsanalyse, die DocTool- und GPT-Bewertungen gegenüberstellt.
-   `javadoc_analysis.csv` & `javadoc_analysis.xlsx`: Rohdaten und qualitative Analyse der Bewertungsfähigkeiten.
-   `javadoc_comment_metrics.csv` & `javadoc_comment_metrics.xlsx`: Auswertung der sprachlichen Metriken der verschiedenen Dokumentationsversionen.

### `Original Java Dateien/`

Dieser Ordner enthält die 11 originalen Java-Dateien, die von den Studierendenprojekten stammen. Diese Dateien enthielten die ursprünglichen, manuell erstellten Javadoc-Kommentare und bildeten die Grundlage für das Analyse-Experiment.

### `Generierung/`

Dieser Ordner enthält alle Dateien, die für das Generierungs-Experiment verwendet wurden. Alle Javadoc-Kommentare wurden aus diesen Dateien vor der Bearbeitung durch die KI entfernt.

-   **`sprechend/`**: Enthält die 11 Java-Dateien mit ihren ursprünglichen, aussagekräftigen Bezeichnern (Methoden-, Variablennamen etc.).
    -   **`Generated/`**: Enthält die von der KI generierten Javadoc-Kommentare für die Dateien aus dem Ordner `sprechend/`.

-   **`nicht sprechend/`**: Enthält die 11 Java-Dateien, nachdem alle aussagekräftigen Bezeichner durch zufällige Zeichenketten ersetzt wurden (Obfuskation).
    -   `obfuscate.py`: Das Python-Skript, das zur Obfuskation der Java-Dateien verwendet wurde.
    -   **`Generated/`**: Enthält die von der KI generierten Javadoc-Kommentare für die obfuskierten Dateien aus dem Ordner `nicht sprechend/`.

### `Grafiken/`

Dieser Ordner enthält alle in der Bachelorarbeit verwendeten Grafiken und Diagramme im SVG-Format. Die Plot-Skripte (`plots_analyze.py` und `plots_generated.py`) exportieren ihre SVG-Dateien automatisch in diesen Ordner.

-   `alibi_score_comparison.svg`: Vergleich der durchschnittlichen Alibi-Scores.
-   `classification_accuracy.svg`: Vergleich der Klassifikationsgenauigkeit von DocTool und GPT.
-   `better_when_equal.svg`: Analyse der Fälle mit übereinstimmender Klassifikation.
-   `javadoc_metrics_extended.svg`: Vergleich der Kommentarstruktur und logischen Fehler.

## Reproduktion der Ergebnisse

Die in den CSV-Dateien enthaltenen Daten können mithilfe der Python-Skripte im Hauptverzeichnis reproduziert oder weiter analysiert werden. Die manuelle Auswertung, die die Basis für die statistische Analyse bildet, ist in der `Auswertung.xlsx` dokumentiert.

Zur Ausführung der Skripte werden gängige Bibliotheken wie `pandas`, `matplotlib`, `seaborn` sowie die Bibliothek `javalang` benötigt.

```bash
pip install pandas numpy matplotlib javalang openpyxl
```
*(Hinweis: `openpyxl` hinzugefügt, falls die Skripte auch die `.xlsx`-Dateien lesen sollen)*

## Lizenz

Der Inhalt dieses Repositories wird unter der [MIT-Lizenz](LICENSE) veröffentlicht.
