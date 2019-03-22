/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.Plantacion.util.FacesUtil;

/**
 *
 * @author ContactCntr4
 */
@Named(value = "jasperReportUtil")
@Dependent
public class JasperReportUtil implements Serializable {

//    @Resource(name = "contratoDS")
//    private DataSource contratoDS;
    private static final Logger LOG = Logger.getLogger(JasperReportUtil.class.getName());
    private static final long serialVersionUID = -5211258823721585036L;


    public final static String PATH;

    public final static String NOMBRE_BEAN;
    public final static String TIPO_PDF;
    public final static String TIPO_XLS;
    public final static String TIPO_CSV;
    public final static String TIPO_HTML;
    //reporte contratos
    public final static String PATH_REPORTE_CRUZADO;
    public final static String PATH_REPORTE_CRUZADO_DETALLE;
    
    @Resource(mappedName = "sch_plantacion")
    private DataSource dataSource;

    static {
        PATH = FacesUtil.getServletContext().getRealPath("reportes") + File.separator;
        PATH_REPORTE_CRUZADO = PATH + "reporteCruce.jasper";
        PATH_REPORTE_CRUZADO_DETALLE = PATH + "DetallePlantacion.jasper";
        
          NOMBRE_BEAN = "jasperReportUtil";
        TIPO_PDF = "application/pdf";
        TIPO_XLS = "application/vnd.ms-excel";
        TIPO_CSV = "application/csv";
        TIPO_HTML = "text/html";
}

    /**
     * Creates a new instance of JasperReportUtil
     */
    public JasperReportUtil() {
    }
    /**
     * Genera el reporte y lo visualiza en el browser.
     *
     * @param urlReporte Path del archivo jasper.
     * @param tipo Tipo de reporte, por el momento solo se generan pdf
     * @param idPlantacion
     * @param params Parámetros para el reporte
     * @throws ClassNotFoundException
     */
    public void generarReporteBD(final String urlReporte, String tipo, final Long idPlantacion, Map<String, Object> params) throws ClassNotFoundException {
        try {
            ExternalContext econtext = FacesUtil.getExternalContext();
            FacesContext fcontext = FacesUtil.getFacesContext();
            try (Connection conn = this.dataSource.getConnection()) {
                Exporter exporter = null;

                InputStream inputStream = new FileInputStream(urlReporte);
                if (inputStream == null) {
                    throw new ClassNotFoundException("Archivo " + urlReporte + " no se encontró");
                }
                JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, conn);
                jasperPrint.setProperty("net.sf.jasperreports.export.character.encoding", "UTF-8");
                HttpServletResponse response = (HttpServletResponse) econtext.getResponse();
                response.setContentType(tipo);
//                response.setHeader("Content-Disposition", "attachment; filename=\"reporte" + nombrePersona + ".pdf\";");
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);
                if ("application/pdf".equals(tipo)) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"reporte" + idPlantacion.toString() + ".pdf\";");
                    exporter = new JRPdfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                }
//                if ("text/html".equals(tipo)) {
//                    exporter = new JRHtmlExporter();
//                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
////                    exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
//                    // System.out.println("Exportando a HTML");
//                }
//                if ("application/rtf".equals(tipo)) {
//                    exporter = new JRRtfExporter();
//                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//                    // System.out.println("Exportando a RTF");
//                }
                if ("application/csv".equals(tipo)) {
                    exporter = new JRCsvExporter();
                    response.setHeader("Content-Disposition", "attachment; filename=\"reporte" + idPlantacion + ".csv\";");
                    SimpleCsvExporterConfiguration config = new SimpleCsvExporterConfiguration();
                    config.setFieldDelimiter("|");

                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
                    exporter.setConfiguration(config);
//                    // System.out.println("Exportando a CSV");
                }
                if ("application/vnd.ms-excel".equals(tipo)) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"reporte" + idPlantacion + ".xls\";");
                    exporter = new JRXlsExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                }
                if (exporter != null) {
                    exporter.exportReport();
                }
            } catch (ClassNotFoundException | JRException | IOException ex) {
                LOG.log(Level.SEVERE, "Error al generar el reporte. ", ex);
            }
            fcontext.responseComplete();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Error al ejecutar la sentecia sql del reporte. ", ex);
        }
    }

    /**
     * *
     * genera un reporte con los valores enviado en los parámetros.
     *
     * @param urlReporte Path del archivo *.jasper
     * @param tipo PDF,XML, etc
     * @param nombreReporte Nombre del archivo
     * @param params Parámetros del reporte
     * @throws ClassNotFoundException
     */
    public void generarReporte(final String urlReporte, String tipo, final String nombreReporte, Map<String, Object> params) throws ClassNotFoundException {
        ExternalContext econtext = FacesUtil.getExternalContext();
        FacesContext fcontext = FacesUtil.getFacesContext();
        try {
            Exporter exporter = null;
            InputStream inputStream = new FileInputStream(urlReporte);
            if (inputStream == null) {
                throw new ClassNotFoundException("Archivo " + urlReporte + " no se encontró");
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params);
            HttpServletResponse response = (HttpServletResponse) econtext.getResponse();
            response.setContentType(tipo);
//            response.setHeader("Content-Disposition", "attachment; filename=\"actividades" + nombrePersona + ".pdf\";");
            //            response.setHeader("Cache-Control", "no-cache");
            //            response.setHeader("Pragma", "no-cache");
            //            response.setDateHeader("Expires", 0);
            if ("application/pdf".equals(tipo)) {
                exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            }
//                if ("text/html".equals(tipo)) {
//                    exporter = new JRHtmlExporter();
//                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
////                    exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
//                    // System.out.println("Exportando a HTML");
//                }
//                if ("application/rtf".equals(tipo)) {
//                    exporter = new JRRtfExporter();
//                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//                    // System.out.println("Exportando a RTF");
//                }
//                if ("application/csv".equals(tipo)) {
//                    exporter = new JRCsvExporter();
//                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//                    // System.out.println("Exportando a CSV");
//                }
//                if ("application/vnd.ms-excel".equals(tipo)) {
//                    exporter = new JRXlsExporter();
//
//                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//                    exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//                    exporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
//                    exporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
//                    exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//                    exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
//                            Boolean.TRUE);
//                }
            if (exporter != null) {
                exporter.exportReport();
            }
        } catch (ClassNotFoundException | JRException | IOException ex) {
            LOG.log(Level.SEVERE, "Error al generar el reporte. ", ex);
        }
        fcontext.responseComplete();
    }

    public void jasperReportBean(final String urlReporte, String tipo, Map<String, Object> params,
            JRDataSource dataSource) throws ClassNotFoundException {
//        try {
        ExternalContext econtext = FacesUtil.getExternalContext();
        FacesContext fcontext = FacesUtil.getFacesContext();
        try {
            Exporter exporter = null;

            InputStream inputStream = new FileInputStream(urlReporte);
            if (inputStream == null) {
                throw new ClassNotFoundException("Archivo " + urlReporte + " no se encontró");
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
            HttpServletResponse response = (HttpServletResponse) econtext.getResponse();
            response.setContentType(tipo);
//            response.setHeader("Content-Disposition", "attachment; filename=\"actividades.pdf\";");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            if ("application/pdf".equals(tipo)) {
                response.setHeader("Content-Disposition", "attachment; filename=\"actividades.pdf\";");
                exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            }
//                if ("text/html".equals(tipo)) {
//                    exporter = new JR
//                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
////                    exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
//                    // System.out.println("Exportando a HTML");
//                }
//                if ("application/rtf".equals(tipo)) {
//                    exporter = new JRRtfExporter();
//                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//                    // System.out.println("Exportando a RTF");
//                }
            if ("application/csv".equals(tipo)) {
                response.setHeader("Content-Disposition", "attachment; filename=\"reporte.csv\";");
                exporter = new JRCsvExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
            }
            if ("application/vnd.ms-excel".equals(tipo)) {
                response.setHeader("Content-Disposition", "attachment; filename=\"reporte.xls\";");
//                exporter = new JRXlsExporter();
//                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

                exporter = new JRXlsExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
                configuration.setOnePagePerSheet(true);
                configuration.setCollapseRowSpan(true);
                configuration.setDetectCellType(false);
                exporter.setConfiguration(configuration);
            }
            if (exporter != null) {
                exporter.exportReport();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al generar el reporte. ", ex);
        }
        fcontext.responseComplete();
//        } catch (IOException ex) {
//            LOG.log(Level.SEVERE, "Error al generar el reporte. ", ex);
//        }
    }

    /**
     * Genera el pdf del estracto del contrato.
     *
     * @param idContrato Código del contrato
     * @throws ClassNotFoundException
     */
    public synchronized void generarExtractoContrato(final Long idPlantacion) throws ClassNotFoundException {
        Map<String, Object> prm = new HashMap<>();
       prm.put("id_plantacion", idPlantacion);
       prm.put("pathDetalle", PATH_REPORTE_CRUZADO_DETALLE);
        this.generarReporteBD(PATH_REPORTE_CRUZADO, TIPO_PDF, idPlantacion, prm);
    }


}
