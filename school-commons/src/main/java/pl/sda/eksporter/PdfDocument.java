package pl.sda.eksporter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Rectangle.NO_BORDER;

public class PdfDocument {

    private static final String DEFAULT_PDF_PATH = ".";
    private final String outputPath;
    public List<String> columnNames;
    private List<?> objectsToSave;
    private String reportType;
    private static final Font DEFAULT_FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font DEFAULT_FONT_TEXT = new Font(Font.FontFamily.TIMES_ROMAN, 10);

    public PdfDocument(List<? extends Object> objects, String outputPath) {
        this.objectsToSave = objects;
        this.columnNames = getFieldsFrom(objects);
        this.outputPath = getOutputPath(outputPath);
    }

    public boolean createPdfDocument() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(String.format(this.outputPath + "/%s_report_%s.pdf", reportType, LocalDate.now().toString())));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            addTitle(document);
            addTable(document);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return false;
        }
        document.close();
        return true;
    }

    private List<String> getFieldsFrom(List<? extends Object> objects) {
        for (Object object : objects) {
            if (object != null) {
                reportType = object.getClass().getSimpleName();
                return Stream.of(object.getClass().getDeclaredFields())
                        .map(Field::getName)
                        .map(String::toUpperCase).collect(Collectors.toList());
            }
        }
        throw new IllegalArgumentException("Only null elements in list!");
    }

    private void addTitle(Document document) throws DocumentException, IOException {
        //Create Paragraph
        Paragraph paragraph = new Paragraph();
        PdfPTable table = createTable();

        PdfPCell cellOne = new PdfPCell(getImage("hogwarts_logo.png"));
        PdfPCell cellTwo = new PdfPCell();
        cellTwo.addElement(getReportName());
        cellTwo.addElement(getReportDetails());
        cellOne.setBorder(NO_BORDER);
        cellTwo.setBorder(NO_BORDER);
        table.addCell(cellOne);
        table.addCell(cellTwo);

        paragraph.add(table);
        document.add(paragraph);

        document.addAuthor(System.getProperty("user.name"));
        document.addCreationDate();
        document.addTitle("REPORT");
        document.add(new Paragraph(new Phrase("\n")));
    }

    private Element getReportName() {
        return new Phrase("REPORT OF " + reportType.toUpperCase(), DEFAULT_FONT_TITLE);
    }

    private Element getReportDetails() {
        return new Phrase("This is super report.\n" + "Generated on:\t" + getCreationDate() + "\nBy: " + System.getProperty("user.name"));
    }

    private PdfPTable createTable() throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new float[]{1, 2});
        table.setWidthPercentage(80);
        return table;
    }

    private Image getImage(String imageName) throws IOException, BadElementException {
        ClassLoader classLoader = getClass().getClassLoader();
        Image image = Image.getInstance(Objects.requireNonNull(classLoader.getResource("images/" + imageName)));
        image.scaleAbsolute(100, 100);
        return image;
    }

    private void addTable(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(columnNames.size()); // length of list
        table.setWidthPercentage(100);
        addTableHeader(table, columnNames);
        addRows(table);
        document.add(table);
    }

    private void addTableHeader(PdfPTable table, List<String> listOfFields) {
        listOfFields.forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle, DEFAULT_FONT_TEXT));
            table.addCell(header);
        });
    }

    private void addRows(PdfPTable table) {
        for (Object object : objectsToSave) {
            if (object != null) {
                addCells(object, table);
            }
        }
    }

    private void addCells(Object object, PdfPTable table) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);

                PdfPCell horizontalAlignCell = new PdfPCell(new Phrase(value == null ? "" : value.toString(), DEFAULT_FONT_TEXT));
                horizontalAlignCell.setHorizontalAlignment(ALIGN_CENTER);
                table.addCell(horizontalAlignCell);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCreationDate() {
        return String.format("%s %tF, %<tT", "Date:", new Date());
    }

    private static String getOutputPath(String outputPath) {
        return StringUtils.isBlank(outputPath) ? DEFAULT_PDF_PATH : outputPath.replace("\\", "/");
    }
}
