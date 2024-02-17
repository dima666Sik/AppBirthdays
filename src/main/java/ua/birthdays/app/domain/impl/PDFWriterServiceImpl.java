package ua.birthdays.app.domain.impl;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.domain.FileWriterService;
import ua.birthdays.app.domain.exceptions.OpenFileException;
import ua.birthdays.app.domain.util.Randomize;
import ua.birthdays.app.models.UserFriendsData;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Implementation of the {@link FileWriterService} interface that writes user friend birthday data into a PDF file.
 */
public class PDFWriterServiceImpl implements FileWriterService {
    private static final Logger logger = LogManager.getLogger(PDFWriterServiceImpl.class.getName());

    @Override
    public void writeDataIntoFile(final String patch, final List<UserFriendsData> userFriendsDataList) throws OpenFileException {
        var nameCSVFile = "\\yourFriendsBirthdays" + Randomize.generateRandomize(0, 100000) + ".pdf";

        try {
            var pdfWriter = new PdfWriter(patch.concat(nameCSVFile));
            var pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            var document = new Document(pdfDocument);

            float colTableWidth = 190f;
            var tableThreeColTitle = new Table(new float[]{colTableWidth, colTableWidth, colTableWidth});
            tableThreeColTitle.setBackgroundColor(Color.BLACK, 0.7f);

            tableThreeColTitle.addCell(new Cell().add("Name Friend")
                                                 .setBold()
                                                 .setFontColor(Color.WHITE)
                                                 .setBorder(Border.NO_BORDER));
            tableThreeColTitle.addCell(new Cell().add("Date Birthday`s")
                                                 .setBold()
                                                 .setFontColor(Color.WHITE)
                                                 .setTextAlignment(TextAlignment.CENTER)
                                                 .setBorder(Border.NO_BORDER));
            tableThreeColTitle.addCell(new Cell().add("Count Days Before Birthday")
                                                 .setBold()
                                                 .setFontColor(Color.WHITE)
                                                 .setTextAlignment(TextAlignment.RIGHT)
                                                 .setBorder(Border.NO_BORDER)
                                                 .setMarginRight(15f));
            document.add(tableThreeColTitle);

            var tableThreeColMain = new Table(new float[]{colTableWidth, colTableWidth, colTableWidth});

            for (var userFriendsData : userFriendsDataList) {
                tableThreeColMain.addCell(new Cell().add(userFriendsData.getAboutFriend()
                                                                        .getNameFriend())
                                                    .setBold()
                                                    .setBorder(Border.NO_BORDER));
                tableThreeColMain.addCell(new Cell().add(userFriendsData.getFriendBirthdayDate()
                                                                        .getFriendDate()
                                                                        .toString())
                                                    .setBold()
                                                    .setTextAlignment(TextAlignment.CENTER)
                                                    .setBorder(Border.NO_BORDER));
                tableThreeColMain.addCell(new Cell().add(String.valueOf(userFriendsData.getFriendBirthdayDate()
                                                                                       .getRemindedCountDaysBeforeBirthday()))
                                                    .setBold()
                                                    .setTextAlignment(TextAlignment.RIGHT)
                                                    .setBorder(Border.NO_BORDER)
                                                    .setMarginRight(15f));
            }
            document.add(tableThreeColMain)
                    .setBottomMargin(20f);

            document.close();
        } catch (FileNotFoundException e) {
            logger.info("Error find file .pdf!", e);
            throw new OpenFileException("Error find file .pdf!", e);
        }

    }
}
