package pl.sda.eksporter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.StringUtils;
import pl.sda.model.Student;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Image.getInstance;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class PdfDocument {

    private static final String DEFAULT_PDF_PATH = ".";
    private final String outputPath;
    public List<String> columnNames;
    private List<?> objectsToSave;
    private String reportType;
    private static final Font DEFAULT_FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font DEFAULT_FONT_TEXT = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    private Document document;

    public PdfDocument(List<?> objects, String outputPath) {
        this.objectsToSave = objects;
        this.columnNames = getFieldsFrom(objects);
        this.outputPath = getOutputPath(outputPath);
    }

    public PdfDocument(String outputPath) {
        this.outputPath = getOutputPath(outputPath);
    }

    public boolean generateStudentsSubjectAveraragesReport(Map<Student, Map<String, Double>> studentsWithSubjectsAverages) throws FileNotFoundException, DocumentException {
        columnNames = Arrays.asList("SUBJECT", "AVERAGE");
        String title = "Students' results";
        String message = "Subjects grade averages of each student.\n";

        createDocument(title);
        try {
            addTitle(document, title, message);
            addGradesParagraphs(document, studentsWithSubjectsAverages);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return false;
        }
        document.close();
        return true;
    }

    public boolean generateWith(String title, String message) throws FileNotFoundException, DocumentException {
        createDocument(title);
        try {
            addTitle(document, title, message);
            addTable(document);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return false;
        }
        document.close();
        return true;
    }


    public boolean generate() throws FileNotFoundException, DocumentException {
        createDocument(null);
        try {
            addTitle(document, null, null);
            addTable(document);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return false;
        }
        document.close();
        return true;
    }

    private void createDocument(String title) throws FileNotFoundException, DocumentException {
        document = new Document();
        if (StringUtils.isBlank(title)) {
            PdfWriter.getInstance(document, new FileOutputStream(format(this.outputPath + "/%s_report_%s.pdf", reportType, LocalDate.now().toString())));
        } else {
            PdfWriter.getInstance(document, new FileOutputStream(format(this.outputPath + "/%s_report_%s.pdf", title.replace(" ", "_"), LocalDate.now().toString())));
        }
        document.open();
    }

    private List<String> getFieldsFrom(List<?> objects) {
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

    private void addTitle(Document document, String title, String message) throws DocumentException, IOException {
        //Create Paragraph
        Paragraph paragraph = new Paragraph();
        PdfPTable table = createTitleTable();

        PdfPCell cellOne = new PdfPCell(getImage("hogwarts_logo.png"));
        PdfPCell cellTwo = new PdfPCell();
        cellTwo.addElement(getReportName(title));
        cellTwo.addElement(getReportDetails(message));
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

    private Element getReportName(String text) {
        if (StringUtils.isBlank(text)) {
            return new Phrase("REPORT OF " + reportType.toUpperCase(), DEFAULT_FONT_TITLE);
        } else {
            return new Phrase("REPORT OF " + text.toUpperCase(), DEFAULT_FONT_TITLE);
        }
    }

    private Element getReportDetails(String message) {
        String text;
        if (StringUtils.isBlank(message)) {
            text = "This is super report.\nGenerated on:\t";
        } else {
            text = String.format("%s\n" + "Generated on:\t", message);
        }
        return new Phrase(text + getCreationDate() + "\nBy: " + System.getProperty("user.name"));
    }

    private PdfPTable createTitleTable() throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new float[]{1, 2});
        table.setWidthPercentage(80);
        return table;
    }


    private void addGradesParagraphs(Document document, Map<Student, Map<String, Double>> studentsWithSubjectsAverages) {

        try {
            for (Map.Entry<Student, Map<String, Double>> entry : studentsWithSubjectsAverages.entrySet()) {
                document.add(new Paragraph(new Phrase("\n")));
                Paragraph currentParagraph = new Paragraph();
                Student student = entry.getKey();
                currentParagraph.add(new Phrase(String.format("%s %s (class: %s, %d)", student.getFirstName(),
                        student.getLastName(),
                        student.getClassroom().getClassName(),
                        student.getClassroom().getYear())));
                Map<String, Double> gradesAverages = entry.getValue();
                PdfPTable table = createTable();
                addRowsSubjectGrades(table, gradesAverages);
                currentParagraph.add(table);
                document.add(currentParagraph);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

    private void addRowsSubjectGrades(PdfPTable table, Map<String, Double> gradesAverages) {
        gradesAverages.forEach((k, v) -> {
            PdfPCell pdfPCellSubject = new PdfPCell(new Phrase(k, DEFAULT_FONT_TEXT));
            pdfPCellSubject.setHorizontalAlignment(ALIGN_CENTER);
            PdfPCell pdfPCellAverage = new PdfPCell(new Phrase(v.toString(), DEFAULT_FONT_TEXT));
            pdfPCellAverage.setHorizontalAlignment(ALIGN_CENTER);
            table.addCell(pdfPCellSubject);
            table.addCell(pdfPCellAverage);
        });
    }

    private PdfPTable createTable() {
        PdfPTable table = new PdfPTable(columnNames.size()); // length of list
        table.setWidthPercentage(100);
        addTableHeader(table, columnNames);
        return table;
    }

    private Image getImage(String imageName) throws IOException, BadElementException {
        ClassLoader classLoader = getClass().getClassLoader();
        Image image = getInstance(requireNonNull(classLoader.getResource("images/" + imageName)));
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
            header.setHorizontalAlignment(ALIGN_CENTER);
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
        return format("%s %tF, %<tT", "Date:", new Date());
    }

    private static String getOutputPath(String outputPath) {
        return isBlank(outputPath) ? DEFAULT_PDF_PATH : outputPath.replace("\\", "/");
    }
}
