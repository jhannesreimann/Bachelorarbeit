import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Utility class providing methods to process word lists stored in Excel worksheets.
 *
 * <p>This class offers a static method to read pairs of words (original and translation)
 * from the first sheet of an XLSX workbook. It is designed for simple import tasks
 * and handles resource management and error logging internally. Instantiation is prevented.</p>
 *
 * @author ChatGPT
 * @version 1.0
 * @see Word
 * @see org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
public final class Utilities {

  /**
   * Logger instance for reporting errors and other events within this class.
   *
   * <p>Uses the java.util.logging framework to record exceptions encountered
   * during Excel file processing at appropriate severity levels.</p>
   */
  private static final Logger logger = Logger.getLogger(Utilities.class.getName());

  /**
   * Private constructor to prevent instantiation of this utility class.
   *
   * <p>All functionality is exposed via static methods.</p>
   */
  private Utilities() {}

  /**
   * Reads a sequence of word pairs from the first sheet of an Excel workbook in XLSX format.
   *
   * <p>This method opens the provided InputStream as an Apache POI XSSFWorkbook,
   * iterates through each row in the first sheet, and attempts to read the first two
   * cells as the original word and its translation. Rows with fewer than two cells
   * are silently skipped. In case of an I/O error, a SEVERE log entry is created,
   * and any words parsed before the failure are returned (or an empty list if none).</p>
   *
   * @param inputStream the InputStream of an XLSX file containing word pairs;
   *                    must not be null and should point to a valid workbook
   * @return a List of Word objects constructed from each row's first and second cell;
   *         returns an empty list if no valid rows are found or if an I/O error occurs
   * @see org.apache.poi.xssf.usermodel.XSSFWorkbook
   */
  public static List<Word> readWordsFromExcel(final InputStream inputStream) {
    final List<Word> words = new ArrayList<>();

    try (Workbook workbook = new XSSFWorkbook(inputStream)) {
      final Sheet sheet = workbook.getSheetAt(0);

      for (final Row row : sheet) {
        final Iterator<Cell> cellIterator = row.cellIterator();

        if (cellIterator.hasNext()) {
          final Cell originalCell = cellIterator.next();
          final String original = originalCell.getStringCellValue();

          if (cellIterator.hasNext()) {
            final Cell translationCell = cellIterator.next();
            final String translation = translationCell.getStringCellValue();

            words.add(new Word(original, translation));
          }
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error reading words from Excel file", e);
    }

    return words;
  }
}