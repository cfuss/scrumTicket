package at.oenb.scrum.tickets;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chfuss on 04.02.16.
 */
public class ScrumTickets {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        // Load the input XML document, parse it and return an instance of the Document class.
        Document document = builder.parse(new File("/Users/chfuss/javadev/application/ScrumTickets/src/main/resources/ASTI_Tickets.xml"));

        List<TicketInfo> tickets = new ArrayList<TicketInfo>();
        NodeList itemList = document.getElementsByTagName("item");

        for (int i = 0; i < itemList.getLength(); i++) {

            Element itemElement =  (Element) itemList.item(i);

            TicketInfo ticketInfo = TicketInfo.builder().
                    description(itemElement.getElementsByTagName("description").item(0).getFirstChild().getNodeValue()).
                    priority(itemElement.getElementsByTagName("priority").item(0).getFirstChild().getNodeValue()).
                    ticketNr(itemElement.getElementsByTagName("key").item(0).getFirstChild().getNodeValue()).
                    titel(itemElement.getElementsByTagName("title").item(0).getFirstChild().getNodeValue()).
                    build();
            tickets.add(ticketInfo);
            }

        // Print all employees.
        tickets.stream().forEach(p->System.out.println(p.toString()));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        //writing to console, can write to any output stream such as file
        StringWriter stringEmp = new StringWriter();
        objectMapper.writeValue(stringEmp, tickets);
        System.out.println("Employee JSON is\n"+stringEmp);

        printToPdf(stringEmp.toString());

    }


   static private void printToPdf(String jsonInput){
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(new File("ScrumTickets.jasper"));
        //Convert json string to byte array.
        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsonInput.getBytes());
        //Create json datasource from json stream
        JsonDataSource ds = new JsonDataSource(jsonDataStream);
        //Create HashMap to add report parameters
        Map parameters = new HashMap();
        //Add title parameter. Make sure the key is same name as what you named the parameter in jasper report.
        parameters.put("title", "Sprint Tickets");
        //Create Jasper Print object passing report, parameter json data source.
        JasperPrint jasperPrint = null;
        Workb
            jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
            JasperExportManager.exportReportToPdfFile(jasperPrint,"SprintTickets.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

}