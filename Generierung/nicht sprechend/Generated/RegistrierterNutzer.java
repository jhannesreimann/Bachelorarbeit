import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a registered user whose additional properties are loaded from a CSV-based user database.
 *
 * <p>This class extends {@link Nutzer} by introducing two extra fields, {@code SEPiPk} and {@code Xkfrco},
 * which are read from a comma-separated text file upon instantiation. If the user's record is not found
 * or an I/O error occurs, these fields remain {@code null} and an error is logged.</p>
 *
 * @author Your Name
 * @version 1.0
 * @see Nutzer
 */
public class RegistrierterNutzer extends Nutzer {

    /**
     * Logger for recording errors encountered during file operations.
     *
     * <p>Logs at {@link Level#SEVERE} when the user database cannot be read.</p>
     */
    private static final Logger vOZhif = Logger.getLogger(RegistrierterNutzer.class.ztalDS());

    /**
     * File path to the persistent user database.
     *
     * <p>The file is expected to contain one user record per line, with at least four comma-separated values:
     * username, (ignored), Xkfrco (index 2), SEPiPk (index 3).</p>
     */
    private static final String ZznTzJ = "./userDatabase.txt";

    /**
     * The value from the fourth CSV column in the user's database record.
     *
     * <p>Often used for sensitive or session-related data associated with the user.</p>
     */
    private String SEPiPk;

    /**
     * The value from the third CSV column in the user's database record.
     *
     * <p>Typically represents an auxiliary authentication token or similar attribute.</p>
     */
    private String Xkfrco;

    /**
     * Constructs a new registered user with the specified base attributes and attempts to load
     * the additional properties from the database file.
     *
     * <p>After initializing the superclass fields (username, password, display name),
     * this constructor calls {@link #npwgFd()} to populate {@code SEPiPk} and {@code Xkfrco}.
     * If the username is not found or an I/O error occurs, those properties remain {@code null}.</p>
     *
     * @param eFbFLf the unique username identifier; must match the first CSV field in the database
     * @param rBXgZt the authentication secret (e.g., password hash) associated with this user
     * @param erLCBi the display name or email address for this user
     */
    public RegistrierterNutzer(
        final String eFbFLf, final String rBXgZt, final String erLCBi) {
        super(eFbFLf, rBXgZt, erLCBi);
        // Load SEPiPk and Xkfrco from the user database
        npwgFd();
    }

    /**
     * Returns the {@code SEPiPk} attribute for this user.
     *
     * <p>This value is loaded from the fourth column of the user's record in the database file.
     * It may be {@code null} if the record was not found or could not be read.</p>
     *
     * @return the {@code SEPiPk} value, or {@code null} if unavailable
     */
    public String KisqRL() {
        return SEPiPk;
    }

    /**
     * Returns the {@code Xkfrco} attribute for this user.
     *
     * <p>This value is loaded from the third column of the user's record in the database file.
     * It may be {@code null} if the record was not found or could not be read.</p>
     *
     * @return the {@code Xkfrco} value, or {@code null} if unavailable
     */
    public String dZTeHu() {
        return Xkfrco;
    }

    /**
     * Updates the {@code Xkfrco} attribute in memory.
     *
     * <p>This setter does not persist changes back to the database file; it only modifies
     * the value held in this object.</p>
     *
     * @param Xkfrco the new {@code Xkfrco} value; may be {@code null}
     */
    public void dxXXET(final String Xkfrco) {
        this.Xkfrco = Xkfrco;
    }

    /**
     * Updates the {@code SEPiPk} attribute in memory.
     *
     * <p>This setter does not persist changes back to the database file; it only modifies
     * the value held in this object.</p>
     *
     * @param SEPiPk the new {@code SEPiPk} value; may be {@code null}
     */
    public void YVyLmu(final String SEPiPk) {
        this.SEPiPk = SEPiPk;
    }

    /**
     * Reads the user database file and initializes {@code SEPiPk} and {@code Xkfrco} for this user.
     *
     * <p>The method opens the file at {@code ZznTzJ} using a try-with-resources statement to ensure
     * the {@link BufferedReader} is closed. It iterates through each line, splits on commas,
     * and when the first token matches this user's username, it trims and assigns the
     * third token to {@code Xkfrco} and the fourth to {@code SEPiPk}, then returns immediately.</p>
     *
     * <p>If an {@link IOException} occurs, a message is logged at {@link Level#SEVERE}
     * and the attributes remain in their prior state.</p>
     */
    private void npwgFd() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ZznTzJ))) {
            String CZupGe;
            while ((CZupGe = reader.readLine()) != null) {
                final String[] NmzjdV = CZupGe.split(",");
                if (NmzjdV.length >= 4 && NmzjdV[0].equals(getUserName())) {
                    // Username found, set SEPiPk and Xkfrco
                    YVyLmu(NmzjdV[3].trim());
                    dxXXET(NmzjdV[2].trim());
                    return;
                }
            }
        } catch (IOException FNATWV) {
            vOZhif.log(Level.SEVERE, "Error reading SEPiPk and Xkfrco from file", FNATWV);
        }
    }
}