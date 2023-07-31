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
import ua.birthdays.app.domain.exceptions.DomainException;
import ua.birthdays.app.domain.interfaces.FileWriterService;
import ua.birthdays.app.domain.util.Randomize;
import ua.birthdays.app.models.UserFriendsData;

import java.io.FileNotFoundException;
import java.util.List;

public class PDFWriterServiceImpl implements FileWriterService {
    @Override
    public void writeDataIntoFile(String patch, List<UserFriendsData> userFriendsDataList) throws DomainException {
        String nameCSVFile = "\\yourFriendsBirthdays" + Randomize.generateRandomize(0, 100000) + ".pdf";

        try {
            PdfWriter pdfWriter = new PdfWriter(patch.concat(nameCSVFile));
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDocument);

            float colTableWidth = 190f;
            Table tableThreeColTitle = new Table(new float[]{colTableWidth, colTableWidth, colTableWidth});
            tableThreeColTitle.setBackgroundColor(Color.BLACK,0.7f);

            tableThreeColTitle.addCell(new Cell().add("Name Friend").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
            tableThreeColTitle.addCell(new Cell().add("Date Birthday`s").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            tableThreeColTitle.addCell(new Cell().add("Count Days Before Birthday").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
            document.add(tableThreeColTitle);

            Table tableThreeColMain = new Table(new float[]{colTableWidth, colTableWidth, colTableWidth});

            for (UserFriendsData userFriendsData : userFriendsDataList) {
                tableThreeColMain.addCell(new Cell().add(userFriendsData.getAboutFriend().getNameFriend()).setBold().setBorder(Border.NO_BORDER));
                tableThreeColMain.addCell(new Cell().add(userFriendsData.getFriendBirthdayDate().getFriendDate().toString()).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                tableThreeColMain.addCell(new Cell().add(String.valueOf(userFriendsData.getFriendBirthdayDate().getRemindedCountDaysBeforeBirthday())).setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
            }
            document.add(tableThreeColMain).setBottomMargin(20f);

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
