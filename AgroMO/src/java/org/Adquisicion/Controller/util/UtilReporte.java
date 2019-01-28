/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Adquisicion.Controller.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author mercy
 */
public class UtilReporte {

    private static final Logger log = Logger.getLogger("UtilReporte");
    protected static final SimpleDateFormat formatoFechaDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * EXPORTAR REPORTE
     *
     * @param listaParaReporte
     * @param nombreReporte
     * @return
     * @throws IOException
     * @throws JRException
     */
    public File exportarReporteBarras(List<? extends Object> listaParaReporte, String nombreReporte) throws IOException, JRException {
        try {
            if (nombreReporte == null) {
                nombreReporte = "Reporte";
            }
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String path = request.getSession().getServletContext().getRealPath("/resources/reportes/ReporteBarras.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(listaParaReporte));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setHeader("Content-Type", "application/xls");
            response.setHeader("Content-Disposition", "attachment; filename=" + nombreReporte + ".xls");
            //JasperExportManager.(jasperPrint, response.getOutputStream());
            //FacesContext.getCurrentInstance().responseComplete();
            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.exportReport();
            FacesContext.getCurrentInstance().responseComplete();
            //response.getOutputStream().flush();
            //response.getOutputStream().close();
            return null;

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al exportar el reporte de apelaciones", e);
            return null;
        }
    }

    /**
     * EXPORTAR REPORTE
     *
     * @param listaParaReporte
     * @param nombreReporte
     * @return
     * @throws IOException
     * @throws JRException
     */
    public File exportarReporteAsignacion(List<? extends Object> listaParaReporte, String nombreReporte) throws IOException, JRException {
        try {
            if (nombreReporte == null) {
                nombreReporte = "Reporte";
            }

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String path = request.getSession().getServletContext().getRealPath("/resources/reportes/reporteAsignacion.jrxml");
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("DIR_LOGO", request.getSession().getServletContext().getRealPath("/resources/reportes/logo.png"));

            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(listaParaReporte));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + nombreReporte + ".pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            FacesContext.getCurrentInstance().responseComplete();
            //response.getOutputStream().flush();
            //response.getOutputStream().close();
            return null;

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al exportar el reporte de apelaciones", e);
            return null;
        }
    }

    /**
     * EXPORTAR REPORTE
     *
     * @param listaParaReporte
     * @param nombreReporte
     * @return
     * @throws IOException
     * @throws JRException
     */
    public File exportarReporteAdquisicion(List<? extends Object> listaParaReporte, String nombreReporte) throws IOException, JRException {
        try {
            if (nombreReporte == null) {
                nombreReporte = "Reporte";
            }

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String path = request.getSession().getServletContext().getRealPath("/resources/reportes/reporteAdquisicion.jrxml");
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("DIR_LOGO", request.getSession().getServletContext().getRealPath("/resources/reportes/logo.png"));

            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(listaParaReporte));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + nombreReporte + ".pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            FacesContext.getCurrentInstance().responseComplete();
            //response.getOutputStream().flush();
            //response.getOutputStream().close();
            return null;

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al exportar el reporte de apelaciones", e);
            return null;
        }
    }

}
