package utils;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class docUtils {


    public static void createDoc(List<String> list) {
        XWPFDocument docxModel = new XWPFDocument();
        CTSectPr ctSectPr = docxModel.getDocument().getBody().addNewSectPr();

        XWPFParagraph bodyParagraph = docxModel.createParagraph();
        bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setBold(true);
        paragraphConfig.setFontSize(20);
        // HEX цвет без решетки #
        paragraphConfig.setFontFamily("Times New Roman");
        paragraphConfig.setText("Company");
        bodyParagraph.createRun().addBreak();
        bodyParagraph.createRun().addBreak();
        bodyParagraph.createRun().addBreak();
        bodyParagraph.createRun().addBreak();
        bodyParagraph.createRun().addBreak();


        XWPFParagraph upParagraph = docxModel.createParagraph();
        upParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun customer = upParagraph.createRun();
        customer.setFontSize(13);
        customer.setFontFamily("Times New Roman");
        List<List<String>> table = dbUtils.getTable("Заказчики");
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).get(0).equals(list.get(4))) {
                customer.setText("Заказчик: " + table.get(i).get(1));
                break;
            }
        }
        upParagraph.createRun().addBreak();

        XWPFRun place = upParagraph.createRun();
        place.setFontSize(13);
        place.setFontFamily("Times New Roman");
        place.setText("Место работы: " + list.get(1));
        upParagraph.createRun().addBreak();

        XWPFRun date = upParagraph.createRun();
        date.setFontSize(13);
        date.setFontFamily("Times New Roman");
        date.setText("Дата: " + list.get(2) + " - " + list.get(3));
        upParagraph.createRun().addBreak();

        upParagraph.createRun().addBreak();
        upParagraph.createRun().addBreak();
        upParagraph.createRun().addBreak();

        XWPFParagraph downParagraph = docxModel.createParagraph();
        downParagraph.setAlignment(ParagraphAlignment.LEFT);

        List<List<String>> technicTable = dbUtils.getTable("Техника");
        XWPFRun technic = downParagraph.createRun();
        technic.setFontSize(11);
        technic.setFontFamily("Times New Roman");
        downParagraph.createRun().addBreak();
        XWPFRun technic1 = downParagraph.createRun();
        technic1.setFontSize(11);
        technic1.setFontFamily("Times New Roman");
        downParagraph.createRun().addBreak();
        XWPFRun technic2 = downParagraph.createRun();
        technic2.setFontSize(11);
        technic2.setFontFamily("Times New Roman");
        downParagraph.createRun().addBreak();
        XWPFRun technic3 = downParagraph.createRun();
        technic3.setFontSize(11);
        technic3.setFontFamily("Times New Roman");
        for (int i = 0; i < technicTable.size(); i++) {
            if (technicTable.get(i).get(0).equals(list.get(5))) {
                technic.setText("Техника ");
                technic1.setText("      Наименование: " + technicTable.get(i).get(1));
                technic2.setText("      Год создания: " + technicTable.get(i).get(2));
                technic3.setText("      Общая масса(кг): " + technicTable.get(i).get(3));
            }
        }
        downParagraph.createRun().addBreak();

        List<List<String>> workerTable = dbUtils.getTable("Рабочие");
        XWPFRun worker = downParagraph.createRun();
        worker.setFontSize(11);
        worker.setFontFamily("Times New Roman");
        downParagraph.createRun().addBreak();
        XWPFRun worker1 = downParagraph.createRun();
        worker1.setFontSize(11);
        worker1.setFontFamily("Times New Roman");
        downParagraph.createRun().addBreak();
        XWPFRun worker2 = downParagraph.createRun();
        worker2.setFontSize(11);
        worker2.setFontFamily("Times New Roman");
        downParagraph.createRun().addBreak();
        XWPFRun worker3 = downParagraph.createRun();
        worker3.setFontSize(11);
        worker3.setFontFamily("Times New Roman");
        for (int i = 0; i < workerTable.size(); i++) {
            if (workerTable.get(i).get(0).equals(list.get(6))) {
                worker.setText("Рабочий");
                worker1.setText("      ФИО: " + workerTable.get(i).get(1));
                worker2.setText("      Номер телефона: " + workerTable.get(i).get(2));
                worker3.setText("      Дата рождения: " + workerTable.get(i).get(3));
            }
        }
        downParagraph.createRun().addBreak();

        List<List<String>> repairTable = dbUtils.getTable("Виды ремонта");
        XWPFRun repair = downParagraph.createRun();
        repair.setFontSize(11);
        repair.setFontFamily("Times New Roman");
        for (int i = 0; i < repairTable.size(); i++) {
            if (repairTable.get(i).get(0).equals(list.get(6))) {
                repair.setText("Вид ремонта: " + repairTable.get(i).get(1));
            }
        }
        for (int i = 0; i < 10; i++) {
            downParagraph.createRun().addBreak();
        }

        XWPFParagraph dateParagraph = docxModel.createParagraph();
        dateParagraph.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun down = dateParagraph.createRun();
        down.setFontSize(11);
        down.setFontFamily("Times New Roman");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        down.setText(format.format(new Date()) + "                                                                                                                              Подпись: ");

        // сохраняем модель docx документа в файл
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("U:\\Programming\\Company\\src\\main\\resources\\Отчет.docx");
            docxModel.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
