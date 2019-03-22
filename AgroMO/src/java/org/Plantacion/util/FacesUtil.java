/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Plantacion.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

/**
 *
 * @author mrl
 */
public class FacesUtil implements Serializable {

    private static final Logger LOG = Logger.getLogger(FacesUtil.class.getName());

    private static final long serialVersionUID = -8960007012132512261L;
//    private static final ResourceBundle resourceBundle;
    //public static final String LAST_CONTRATO, LAST_IESS, LAST_JORNADA, LAST_ACTA, LAST_PAGO_ACTA, LAST_ADENDUM;
    public static final int DAY_MONT_YEAR = 0x001;
    public static final int DAY_YEAR_MONT = 0x002;
    public static final int YEAR_MONT_DAY = 0x003;
    public static final int YEAR_DAY_MONT = 0x004;
    public static String days[] = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
    public static String daysDomingo[] = {"Lunes", "Martes", "Miércoles", "Jueves"};
    public static String daysLunes[] = {"Martes", "Miércoles", "Jueves", "Viernes"};
    public static String daysMartes[] = {"Miércoles", "Jueves", "Viernes", "Sábado"};
    public static String daysMiercoles[] = {"Jueves", "Viernes", "Sábado", "Domingo"};
    public static String daysJueves[] = {"Viernes", "Sábado", "Domingo", "Lunes"};
    public static String daysViernes[] = {"Sábado", "Domingo", "Lunes", "Martes"};
    public static String daysSabado[] = {"Domingo", "Lunes", "Martes", "Miércoles"};
    //para contratos 16267
    public static String daysDomingo1[] = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
    public static String daysLunes1[] = {"Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
    public static String daysMartes1[] = {"Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    public static String daysMiercoles1[] = {"Jueves", "Viernes", "Sábado", "Domingo", "Lunes"};
    public static String daysJueves1[] = {"Viernes", "Sábado", "Domingo", "Lunes", "Martes"};
    public static String daysViernes1[] = {"Sábado", "Domingo", "Lunes", "Martes", "Miércoles"};
    public static String daysSabado1[] = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves"};

    public static String Sabado = "Sábado";
    public static String Domingo = "Domingo";

    public static String months[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    /**
     * Obtiene el contexto del sistema.
     *
     * @return FacesContext
     */
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Obtiene el ExternalContext del sistema.
     *
     * @return ExternalContext
     */
    public static ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }

    public static HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) getExternalContext().getResponse();
    }

    /**
     * Ingresa el valor de una variable a session.
     *
     * @param nombre Nombre de la variable.
     * @param valor Valor de la variable.
     */
    public static void ingresarParametroSession(final String nombre, Object valor) {
        HttpSession session = (HttpSession) getExternalContext().getSession(true);
        session.setAttribute(nombre, valor);
    }

    /**
     * Obtiene el parámetro ingresado en sesión.
     *
     * @param nombre Nombre del parámetros
     * @return Parámetro
     */
    public static Object obtenerParametroSession(final String nombre) {
        HttpSession session = (HttpSession) getExternalContext().getSession(true);
        return session.getAttribute(nombre);
    }

    /**
     * Elimina el valor de una variable a session.
     *
     * @param nombre Nombre de la variable.
     */
    public static void eliminarParametroSession(final String nombre) {
        HttpSession session = (HttpSession) getExternalContext().getSession(true);
        session.removeAttribute(nombre);
    }


    public static void ejecutarScript(String script) {
        getRequestContext().execute(script);
    }

    public static RequestContext getRequestContext() {
        return RequestContext.getCurrentInstance();
    }

    public static void abrirDialogo(String widgetVar) {
        ejecutarScript("PF('" + widgetVar + "').show()");
    }

    /**
     * Obtiene el URL de la aplicación.
     *
     * @return
     */
    public static String getApplicationUri() {
        try {
            URI uri = new URI(getExternalContext().getRequestScheme(),
                    null, getExternalContext().getRequestServerName(), getExternalContext().getRequestServerPort(),
                    getExternalContext().getRequestContextPath(), null, null);
            return uri.toASCIIString();
        } catch (URISyntaxException e) {
            throw new FacesException(e);
        }
    }

    /**
     * Redirecciona a una página.
     *
     * @param url Página a la cual se direcciona el sistema.
     * @throws IOException Lanzada si no existe el url
     */
    public static void redireccionar(final String url) throws IOException {
        getExternalContext().redirect(url);
    }

    /**
     * Permite agregar un mensaje de error al FacesContext.
     *
     * @param mensaje Mensaje de error
     */
    public static void mensajeError(final String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", mensaje);
        getFacesContext().addMessage(null, msg);
    }

    /**
     * Permite agregar un mensaje de error al FacesContext.
     *
     * @param idComponente
     * @param mensaje Mensaje de error
     */
    public static void mensajeError(final String idComponente, final String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", mensaje);
        getFacesContext().addMessage(idComponente, msg);
    }

    /**
     * Permite agregar un mensaje de error al FacesContext.
     *
     * @param idComponente
     * @param mensaje Mensaje de error
     */
    public static void mensajeContenidoError(final String idComponente, final String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, idComponente, mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(msg);
//        getFacesContext().addMessage(null, msg);
    }

    /**
     * Despliega un mensaje de error en dialog.
     *
     * @param mensaje
     */
    public static void mensajeErrorDialog(final String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(msg);
    }

    /**
     * Permite agregar un mensaje de error fatal al FacesContext.
     *
     * @param mensaje Mensaje de error
     */
    public static void mensajeFatal(final String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, " ", mensaje);
        getFacesContext().addMessage(null, msg);
    }

    /**
     * Permite agregar un mensaje de advertencia al FacesContext.
     *
     * @param mensaje Mensaje de error
     */
    public static void mensajeWarn(final String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", mensaje);
        getFacesContext().addMessage(null, msg);
    }

    public static void mensajeWarnDialog(final String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(msg);
    }

    /**
     * Permite agregar un mensaje de información al FacesContext.
     *
     * @param mensaje Mensaje de error
     */
    public static void mensajeInfo(final String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensaje);
        getFacesContext().addMessage(null, msg);
    }

    /**
     * Despliega un mensaje de información en un dialog.
     *
     * @param mensaje
     */
    public static void mensajeInfoDialog(final String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(msg);
    }

    public static ServletContext getServletContext() {
        return (ServletContext) getExternalContext().getContext();
    }

    /**
     * Permite agregar meses a una determinada fecha
     *
     * @param fecha Fecha de partida
     * @param months Número de meses a sumar.
     * @return
     */
    public static synchronized Date sumarMeses(Date fecha, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.MONTH, months);
        return c.getTime();
    }

    /**
     * Suma o resta días a la fecha.
     *
     * @param fecha fecha
     * @param dia días a sumar o restar
     * @return fecha
     */
    public static synchronized Date sumarDia(Date fecha, int dia) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.DAY_OF_YEAR, dia);
        return c.getTime();
    }

    /**
     * Suma o resta años a la fecha.
     *
     * @param fecha fecha
     * @param anio Años a sumar o restar
     * @return fecha
     */
    public static synchronized Date sumarAnio(Date fecha, int anio) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.YEAR, anio);
        return c.getTime();
    }

    /**
     * Eliminar un archivo de disco duro.
     *
     * @param pArchivo Path del archivo.
     * @return True.- El archivo fue eliminado. False.- El archivo no fue
     * eliminado.
     */
    public static boolean eliminarArchivoDisco(String pArchivo) {
        File fichero = new File(pArchivo);
        return fichero.delete();
    }

    /**
     * Determina si un archivo existe.
     *
     * @param pArchivo
     * @return True.- El archivo existe. False.- El archivo no existe.
     */
    public static boolean existeArchivoDisco(String pArchivo) {
        File fichero = new File(pArchivo);
        return fichero.exists();
    }


    /**
     * Retorna la fecha de 04 de agosto de 2016. Esta fecha es empleada para
     * restringir el ingreso de la fecha de inicio de teletrabajo.
     *
     * @return Fecha 04/10/2016
     */
    public static Date agosto2016() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        c.set(Calendar.YEAR, 2016);
        c.set(Calendar.MONTH, Calendar.AUGUST);
        c.set(Calendar.HOUR_OF_DAY, 3);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Retorna la fecha de 10 de mayo de 2018. Esta fecha es empleada para
     * restringir el ingreso de los contratos, de acuerdo al redmine 15858
     *
     * @return Fecha 10/05/2018
     */
    public static Date mayo2018() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, Calendar.MAY);
        c.set(Calendar.DAY_OF_MONTH, 10);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Retorna la fecha de 8 de junio de 2018. Esta fecha es empleada para
     * restringir el ingreso de los contratos, de acuerdo al redmine 15858
     *
     * @return Fecha 08/06/2018
     */
    public static Date junio2018() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, Calendar.JUNE);
        c.set(Calendar.DAY_OF_MONTH, 8);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Obtiene el año actual.
     *
     * @return Año
     */
    public static int obtenerPeriodo() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        return c.get(Calendar.YEAR);
    }

    /**
     * Obtiene el año actual.
     *
     * @param fecha Fecha
     * @return Año
     */
    public static int obtenerPeriodo(Date fecha) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        c.setTime(fecha);
        return c.get(Calendar.YEAR);
    }

    /**
     * Obtiene el año actual.
     *
     * @return Año
     */
    public static int obtenerMesNumerico() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        return c.get(Calendar.MONTH);
    }

    /**
     * Obtiene el mes de la fecha actual.
     *
     * @return Año
     */
    public static String obtenerMes() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        return (c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())).toUpperCase();
    }

    /**
     * Obtiene el mes a partir de una fecha especifica enviada.
     *
     * @param fecha
     * @return Año
     */
    public static String obtenerMes(Date fecha) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        c.setTime(fecha);
        return (c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())).toUpperCase();
    }

    /**
     * Obtiene el mes completo en letras por medio de una fecha
     *
     * @param fecha
     * @return *
     */
    public static String obtenerMesCompleto(Date fecha) {

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        c.setTime(fecha);
        return (c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())).toUpperCase();

    }

    /**
     * Estable la fecha de inicio para los reportes y consultas. El valor de la
     * fecha es dd/MM/yyyy 00:00:00
     *
     * @param fecha Fecha
     * @return
     */
    public static Date truncateFechaInicio(final Date fecha) {
        Calendar fechaTruncada = Calendar.getInstance();
        fechaTruncada.setTimeInMillis(fecha.getTime());
        fechaTruncada.set(Calendar.HOUR_OF_DAY, 0);
        fechaTruncada.set(Calendar.MINUTE, 0);
        fechaTruncada.set(Calendar.SECOND, 0);
        fechaTruncada.set(Calendar.MILLISECOND, 0);
        return fechaTruncada.getTime();
    }

    /**
     * Estable la fecha de fin para los reportes y consultas. El valor de la
     * fecha es dd/MM/yyyy 23:59:59
     *
     * @param fecha Fecha
     * @return
     */
    public static Date truncateFechaFin(final Date fecha) {
        Calendar fechaTruncada = Calendar.getInstance();
        fechaTruncada.setTimeInMillis(fecha.getTime());
        fechaTruncada.set(Calendar.HOUR_OF_DAY, 23);
        fechaTruncada.set(Calendar.MINUTE, 59);
        fechaTruncada.set(Calendar.SECOND, 59);
        fechaTruncada.set(Calendar.MILLISECOND, 59);
        return fechaTruncada.getTime();
    }

    public static Date truncateInicioFecha(final Date fecha) {
        Calendar fechaTruncada = Calendar.getInstance();
        fechaTruncada.setTimeInMillis(fecha.getTime());
        fechaTruncada.set(Calendar.DAY_OF_MONTH, 1);
        fechaTruncada.set(Calendar.HOUR_OF_DAY, 0);
        fechaTruncada.set(Calendar.MINUTE, 0);
        fechaTruncada.set(Calendar.SECOND, 0);
        fechaTruncada.set(Calendar.MILLISECOND, 0);
        return fechaTruncada.getTime();
    }

    /**
     * Estable la fecha de fin para el registro de contratos. El valor de la
     * fecha es dd/MM/yyyy 23:59:00
     *
     * @param fecha Fecha
     * @return
     */
    public static Date truncateFechaLimiteContrato(final Date fecha) {
        Calendar fechaTruncada = Calendar.getInstance();
        fechaTruncada.setTimeInMillis(fecha.getTime());
        fechaTruncada.set(Calendar.HOUR_OF_DAY, 23);
        fechaTruncada.set(Calendar.MINUTE, 59);
        fechaTruncada.set(Calendar.SECOND, 00);
        fechaTruncada.set(Calendar.MILLISECOND, 00);
        return fechaTruncada.getTime();
    }


    /**
     * Obtiene la fecha en un formato específico.
     *
     * @param date Fecha
     * @param format Formato de la fecha d/m/y
     * @param dateInLetter True.- Fecha en letras
     * @return Fecha fomateada
     */
    public synchronized static String getDate(Date date, int format, boolean dateInLetter) {
        return getDate(date, "/", format, dateInLetter, false, false);
    }

    public synchronized static String getDate(Date date, int format, boolean dateInLetter, boolean mostrarHoras, boolean mostrarSegundos) {
        return getDate(date, "/", format, dateInLetter, mostrarHoras, mostrarSegundos);
    }

    /**
     * Da formato en palabras a un rango de fechas.
     *
     * @param date
     * @param delimiter
     * @param format
     * @param dateInLetter
     * @param showHour
     * @param showSeconds
     * @return
     */
    private static String getDate(Date date, String delimiter, int format, boolean dateInLetter, boolean showHour, boolean showSeconds) {
        if (date == null) {
            return "";
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        String strDate = "";
        int day = c.get(Calendar.DAY_OF_MONTH);
        int dayW = c.get(Calendar.DAY_OF_WEEK) - 1;
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        switch (format) {
            case DAY_MONT_YEAR:
                if (dateInLetter) {
                    strDate += days[dayW] + " " + day + " de " + months[month] + " de " + year;
                    if (showSeconds) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":"
                                + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
                    } else if (showHour) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":" + (minute < 10 ? ("0" + minute) : minute);
                    }
                } else {
                    strDate += (day < 10 ? ("0" + day) : day) + delimiter + (month < 9 ? "0" + (month + 1) : (month + 1))
                            + delimiter + year;
                    if (showSeconds) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":"
                                + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
                    } else if (showHour) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":" + (minute < 10 ? ("0" + minute) : minute);
                    }
                }
                break;
            case DAY_YEAR_MONT:
                if (dateInLetter) {
                    strDate += days[dayW] + " " + day + " de " + months[month] + " de " + year;
                    if (showSeconds) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":"
                                + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
                    } else if (showHour) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":" + (minute < 10 ? ("0" + minute) : minute);
                    }
                } else {
                    strDate += (day < 10 ? ("0" + day) : day) + delimiter + year + delimiter
                            + (month < 9 ? "0" + (month + 1) : (month + 1));
                    if (showSeconds) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":"
                                + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
                    } else if (showHour) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":" + (minute < 10 ? ("0" + minute) : minute);
                    }
                }
                break;
            case YEAR_DAY_MONT:
                if (dateInLetter) {
                    strDate += days[dayW] + " " + day + " de " + months[month] + " de " + year;
                    if (showSeconds) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":"
                                + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
                    } else if (showHour) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":" + (minute < 10 ? ("0" + minute) : minute);
                    }
                } else {
                    strDate += year + delimiter + (day < 10 ? ("0" + day) : day) + delimiter
                            + (month < 9 ? "0" + (month + 1) : (month + 1));
                    if (showSeconds) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":"
                                + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
                    } else if (showHour) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":" + (minute < 10 ? ("0" + minute) : minute);
                    }
                }
                break;
            case YEAR_MONT_DAY:
                if (dateInLetter) {
                    strDate += days[dayW] + " " + day + " de " + months[month] + " de " + year;
                    if (showSeconds) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":"
                                + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
                    } else if (showHour) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":" + (minute < 10 ? ("0" + minute) : minute);
                    }
                } else {
                    strDate += year + delimiter + (month < 9 ? "0" + (month + 1) : (month + 1)) + delimiter
                            + (day < 10 ? ("0" + day) : day);
                    if (showSeconds) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":"
                                + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
                    } else if (showHour) {
                        strDate += (hour < 10 ? ("  0" + hour) : ("  " + hour)) + ":" + (minute < 10 ? ("0" + minute) : minute);
                    }
                }
                break;
        }
        return strDate;
    }


    /**
     * Calcula la edad de una persona.
     *
     * @param fechaNac Fecha de nacimiento
     * @return edad
     */
    public static int calcularEdad(Date fechaNac) {
        Calendar hoy = Calendar.getInstance();
        Calendar nacimiento = Calendar.getInstance();
        hoy.setTime(new Date());
        nacimiento.setTime(fechaNac);
        int diffAnio = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);
        int diffMes = hoy.get(Calendar.MONTH) - nacimiento.get(Calendar.MONTH);
        int diffDia = hoy.get(Calendar.DAY_OF_MONTH) - nacimiento.get(Calendar.DAY_OF_MONTH);
        // Si está en ese año pero todavía no los ha cumplido
        if ((diffMes < 0) || ((diffMes == 0) && (diffDia < 0))) {
            diffAnio = diffAnio - 1;
        }
        return diffAnio;
    }

    /**
     * Descifra una palabra en Base64
     *
     * @param a Dato a descifrar
     * @return Palabra descifrada
     */
    public static String descifrarBase64(String a) {
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(a);
        String b = new String(decodedByteArray);
        return b;
    }

    /**
     * Cifra una palabra en Base64
     *
     * @param a Palabra a cifrar
     * @return palabra cifrada
     */
    public static String cifrarBase64(String a) {
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        String b = encoder.encodeToString(a.getBytes(StandardCharsets.UTF_8));
        return b;
    }

    /**
     * Obtiene el mes en numero de una fecha
     *
     * @param fecha
     * @return
     */
    public static int obtenerMesNumero(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        return c.get(Calendar.MONTH + 1);
    }

    /**
     * Obtiene el año de una fecha
     *
     * @param fecha
     * @return
     */
    public static int obtenerAño(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        return c.get(Calendar.YEAR);
    }

    /**
     * Obtiene el dia de una fecha
     *
     * @param fecha
     * @return
     */
    public static int obtenerDia(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Obtiene la hora de una fecha
     *
     * @param fecha
     * @return
     */
    public static String obtenerHora(Date fecha) {
        Calendar c = Calendar.getInstance();
        String hora;
        c.setTime(fecha);
        hora = String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        return hora;
    }

    /**
     * Obtiene el número de mediante dos fechas
     *
     * @param fechaFinal
     * @param fechaInicial
     * @return
     */
    public static int diferenciaEnHoras(final Date fechaFinal, final Date fechaInicial) {
        int diferencia = (int) (fechaFinal.getTime() - fechaInicial.getTime()) / 1000;
        int horas;
        horas = (int) Math.floor(diferencia / 3600);
        return horas;

    }

    /**
     * Calcula la edad de una persona.
     *
     * @param fechaNac Fecha de nacimiento
     * @return edad
     */
    public static String calcularEdadCompleta(Date fechaNac) {
        Calendar hoy = Calendar.getInstance();
        Calendar nacimiento = Calendar.getInstance();
        hoy.setTime(new Date());
        nacimiento.setTime(fechaNac);
        String valorMes, valorDia;
        int diffAnio = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);
        int diffMes = hoy.get(Calendar.MONTH) - nacimiento.get(Calendar.MONTH);
        if (diffMes < 0) {
            diffMes = diffMes * -1;
        }
        int diffDia = hoy.get(Calendar.DAY_OF_MONTH) - nacimiento.get(Calendar.DAY_OF_MONTH);
        if (diffDia < 0) {
            diffDia = diffDia * -1;
        }
        // Si está en ese año pero todavía no los ha cumplido
        if ((diffMes < 0) || ((diffMes == 0) && (diffDia < 0))) {
            diffAnio = diffAnio - 1;
        }
        if (diffDia < 10) {
            valorDia = "0" + String.valueOf(diffDia);
        } else {
            valorDia = String.valueOf(diffDia);
        }

        if (diffMes < 10) {
            valorMes = "0" + String.valueOf(diffMes);
        } else {
            valorMes = String.valueOf(diffMes);
        }
        return valorDia + "-" + valorMes + "-" + String.valueOf(diffAnio);
    }

    /**
     * Obtiene la diferencia en dias entre dos fechas .
     *
     * @param fechaNac Fecha de nacimiento
     * @return edad
     */
    public static int diferenciaEntreFechas(Date fechaMayor, Date fechaMenor) {
        long diferenciaFechas = fechaMayor.getTime() - fechaMenor.getTime();
        long resultado = diferenciaFechas / (1000 * 60 * 60 * 24);
        return (int) resultado;
    }

    public static String getSabado() {
        return Sabado;
    }

    public static void setSabado(String Sabado) {
        FacesUtil.Sabado = Sabado;
    }

    public static String getDomingo() {
        return Domingo;
    }

    public static void setDomingo(String Domingo) {
        FacesUtil.Domingo = Domingo;
    }

}
