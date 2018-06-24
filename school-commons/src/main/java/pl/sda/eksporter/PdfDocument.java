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

public class PdfDocument {

    private static final String DEFAULT_PDF_PATH = ".";
    private final String outputPath;
    public List<String> columnNames;
    private List<?> objectsToSave;
    private String reportType;

    public PdfDocument(List<? extends Object> objects, String outputPath) {
        this.objectsToSave = objects;
        this.columnNames = getFieldsFrom(objects);
        this.outputPath = getOutputPath(outputPath);
    }

    public void createPdfDocument() {
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
        }
        document.close();
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

        PdfPTable table = new PdfPTable(2);
        table.setWidths(new float[]{1, 2});
        table.setWidthPercentage(80);
        Phrase phrase = new Phrase("REPORT OF " + reportType, new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));

        PdfPCell cellOne = new PdfPCell(getImage());

        PdfPCell cellTwo = new PdfPCell();
        cellTwo.addElement(phrase);
        cellTwo.addElement(new Phrase("This is super report.\n" + "Generated on:\t" + getCreationDate() + "\nBy: " + System.getProperty("user.name")));
        cellOne.setBorder(Rectangle.NO_BORDER);
        cellTwo.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellOne);
        table.addCell(cellTwo);

        paragraph.add(table);
        document.add(paragraph);

        document.addAuthor(System.getProperty("user.name"));
        document.addCreationDate();
        document.addTitle("REPORT");
        document.add(new Paragraph(new Phrase("\n")));
    }

    private Image getImage() throws IOException, BadElementException {
        ClassLoader classLoader = getClass().getClassLoader();
        Image image = Image.getInstance(Objects.requireNonNull(classLoader.getResource("images/hogwarts_logo.png")));
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
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle, new Font(Font.FontFamily.TIMES_ROMAN, 10)));
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

                PdfPCell horizontalAlignCell = new PdfPCell(new Phrase(value == null ? "" : value.toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10)));
                horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
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
