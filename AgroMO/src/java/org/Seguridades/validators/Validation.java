/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.validators;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author james
 */
@ManagedBean(name = "validaciones")
@SessionScoped
public class Validation {

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final int CEDULA = 0x00A;
    public static final int RUC = 0x00D;
    public static final int PASAPORTE = 0x00F;
    public static final Map<String, String> INVALID_CHARACTERS = new HashMap<String, String>();
    private static final String VALID_CHARACTERS = "0123456789ABCDEFGHIJKLMOPQRSabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final Random r = new Random();
    private static int[] PATTERN = {2, 1, 2, 1, 2, 1, 2, 1, 2};
    private static int[] CASO_9 = {4, 3, 2, 7, 6, 5, 4, 3, 2};
    private static int[] CASO_6 = {3, 2, 7, 6, 5, 4, 3, 2};
    private static final String EMAIL_REGEX = "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name))$";
    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]+$";
    private static final String NUMERIC_REGEX = "^[0-9]+$";

    static {
        INVALID_CHARACTERS.put("\u00C1", "A");
        INVALID_CHARACTERS.put("\u00C9", "E");
        INVALID_CHARACTERS.put("\u00CD", "I");
        INVALID_CHARACTERS.put("\u00D3", "O");
        INVALID_CHARACTERS.put("\u00DA", "U");
        INVALID_CHARACTERS.put("\u00D1", "N");
        INVALID_CHARACTERS.put("\u00E1", "a");
        INVALID_CHARACTERS.put("\u00E9", "e");
        INVALID_CHARACTERS.put("\u00ED", "i");
        INVALID_CHARACTERS.put("\u00F3", "o");
        INVALID_CHARACTERS.put("\u00FA", "u");
        INVALID_CHARACTERS.put("\u00F1", "n");
        INVALID_CHARACTERS.put(" ", "_");

    }

    /**
     * Metodo que cambia un Date a String.
     * @param date  the date
     * @return the string
     */
    public static synchronized String convertirTexto(final Date fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        return dateFormat.format(fecha);
    }

    /**
     * Metodo que cambia un Date a String.
     *
     * @param date
     *            the date
     *
     * @return the string
     */
    public static String convertirTexto(final Date fecha, final String strPatron) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(strPatron);
        return dateFormat.format(fecha);
    }

    /**
     * Copia un archivo de una destino a otro
     * @param fuente
     * @param destino
     * @throws IOException
     */
    public static synchronized void copy(File fuente, File destino) throws IOException {
        InputStream in = new FileInputStream(fuente);
        OutputStream out = new FileOutputStream(destino);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    /**
     * Permite generar de una forma simple unnumero aleatorio co nun algoritmo conbinado
     *
     * @param numChars
     * @return
     */
    public synchronized static String generaNumeroAleatorio(int numChars) {
        char[] buf = new char[numChars];

        for (int i = 0; i < buf.length; i++) {

            buf[i] = NUMBERS.charAt(r.nextInt(NUMBERS.length()));

        }

        return new String(buf);
    }

    /**
     * Permite remover caraceres no deseados de una determinada cadena
     * @param cadenaOriginal
     * @return
     */
    public synchronized static String removerCaracteresNoDeseados(String cadenaOriginal) {
        for (String key : Validation.INVALID_CHARACTERS.keySet()) {
            cadenaOriginal = replace(key, INVALID_CHARACTERS.get(key), cadenaOriginal);
        }
        cadenaOriginal = replace("[^\\\\a-zA-Z_0-9/._-]", "_", cadenaOriginal);
        return cadenaOriginal;
    }

    /**
     * Se encarga de reemplazar las coincidencias de una cadena buscada por otra cadena
     * @param cadenaBuscada
     * @param reemplazarConEsta
     * @param cadenaOriginal
     * @return
     */
    public synchronized static String replace(String cadenaBuscada, String reemplazarConEsta, String cadenaOriginal) {
        Pattern pattern = Pattern.compile(cadenaBuscada);
        Matcher matcher = pattern.matcher(cadenaOriginal);
        String output = matcher.replaceAll(reemplazarConEsta);
        return output;
    }

    /**
     * Transformar a frase un texto
     * @param texto
     *
     * @return the string
     */
    public synchronized static String toPhrase(final String texto) {
        String cadena = "";
        Pattern pattern = Pattern.compile("[A-Z]{1}[a-z]*");
        Matcher matcher = pattern.matcher(texto);
        while (matcher.find()) {
            cadena = cadena.concat(" ").concat(matcher.group());
        }
        return cadena.trim();
    }

    /**
     * Truncar texto.
     *
     * @param texto
     * @param longitud
     *
     * @return the string
     */
    public static String truncarTexto(String texto, int longitud) {
        String textoTruncado = "";
        if (texto.length() > longitud) {
            textoTruncado = texto.substring(0, longitud);
        } else {
            textoTruncado = texto;
        }
        return textoTruncado;
    }
//    public static void main(String[] args) {
//        Validation v= new Validation();
//        System.out.println("dato1"+ v.validateCCRuc("180394171301"));
//        System.out.println("dato2"+ v.validateCCRuc("18039417130001"));
//
//    }

    /**
     * Permite validar una cedula o ruc del Ecuador
     * @param identificacion    Cadena que representa el docuemto de identificacion
     * @param longitud
     * @return
     */
    public static synchronized boolean validateCCRuc(final String identificacion) {

        if (identificacion == null) {
            return false;
        }
        if (identificacion.trim().isEmpty()) {
            return false;
        }

        if (!validateNumberPattern(identificacion)) {
            return false;
        }

        if (identificacion.length() != 10 & identificacion.length() != 13) {
            return false;
        }

        int[] coeficientes = null;
        int indiceDigitoVerificador = 9;
        int modulo = 11;

        if ((identificacion.length() == 13) && !identificacion.substring(10, 13).equals("001")) {
            return false;
        }
        if (identificacion.charAt(2) == Character.valueOf('9')) {
            coeficientes = CASO_9;
        } else if (identificacion.charAt(2) == Character.valueOf('6')) {
            coeficientes = CASO_6;
            indiceDigitoVerificador = 8;
        } else if (identificacion.charAt(2) < Character.valueOf('6')) {
            coeficientes = PATTERN;
            modulo = 10;
        }
        return verify(identificacion.toCharArray(), coeficientes, indiceDigitoVerificador, modulo);
    }

    /**
     * Permite verificar una cedula de acuerdo al digito autoverificador
     * @param array      arreglo de caracteres
     * @param coeficientes   coeficientes de calculo
     * @param indiceDigitoVerificador    Indece autoverificador
     * @param modulo         modulo de verificacion
     * @return
     */
    private static boolean verify(final char[] array, final int[] coeficientes,
            final int indiceDigitoVerificador, final int modulo) {
        if (coeficientes == null) {
            return false;
        }
        int sum = 0;
        int aux = 0;
        for (int i = 0; i < coeficientes.length; i++) {
            aux = new Integer(String.valueOf(array[i])) * coeficientes[i];
            if ((modulo == 10) && (aux > 9)) {
                aux -= 9;
            }
            sum += aux;
        }
        int mod = sum % modulo;
        mod = mod == 0 ? modulo : mod;
        final int res = (modulo - mod);
        Integer valorVerificar = null;
        if (array.length == 13) {
            valorVerificar = Integer.valueOf(String.valueOf(array[array.length - (13 - indiceDigitoVerificador)]));
        } else if (array.length == 10) {
            valorVerificar = Integer.valueOf(String.valueOf(array[array.length - (10 - indiceDigitoVerificador)]));
        }
        if (res != valorVerificar) {
            return false;
        }
        return true;
    }

    /**
     * Permite validar una cadena de acuerdo a un patron
     * @param patron    Patron usado para validacion
     * @param valor     Cadena a validar
     * @return          indica si una determinada cadena paso la validacion
     */
    public static synchronized boolean validatePattern(final String patron, final String valor) {
        final Pattern patter = Pattern.compile(patron);
        final Matcher matcher = patter.matcher(valor);
        return matcher.matches();
    }

    /**
     * Permite validar de acuerdo al patron numerico
     * @param valor cadena a validar
     * @return  Indica si se paso la validacion
     */
    public static synchronized boolean validateNumberPattern(final String valor) {
        return validatePattern(NUMERIC_REGEX, valor);
    }

    /**
     * Permite validar un patron de texto
     * @param valor cadena a validar
     * @return   Indica si se paso la validacion
     */
    public static synchronized boolean validateTextPattern(final String valor) {
        return validatePattern(ALPHANUMERIC_REGEX, valor);
    }

    /**
     * Permite validar un correo electronico de acuerdo a la expresion regular que lo representa
     * @param mail  correo a validar
     * @return      Indica si el correo electronico paso la validacion
     */
    public static synchronized boolean validateMail(String mail) {
        Pattern mask = null;
        mask = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = mask.matcher(mail);
        return matcher.matches();
    }

    public static synchronized boolean validateNumber(int numero) {
        Pattern mask = null;
        mask = Pattern.compile(NUMBERS);
        Matcher matcher = mask.matcher("" + numero);
        return matcher.matches();
    }

    /**
     * Permite realizar la validacion de documento de identificacion de acuerdo al tipo de la misma
     * @param tipoDoc   Indica el tipo de documento a validar
     * @param numDoc    Numero de docuemnteo a validar
     * @return          booleano que indica si ha pasdo o no la validacion
     */
    public static synchronized boolean validateIdentification(int tipoDoc, String numDoc) {
        boolean error = false;
        if (tipoDoc == CEDULA | tipoDoc == RUC) {
            if (numDoc.length() != tipoDoc) {
                return false;
            }
            return Validation.validateCCRuc(numDoc);
        } else if (tipoDoc == PASAPORTE) {
            return !Validation.validateTextPattern(numDoc);
        }
        return error;
    }

    /***
     * permite validar si una fecha no es anterior a la otra
     * @param fechaIni
     * @param fehcaFin
     */
    public static boolean validateAfterDate(Date fechaIni, Date fehcaFin) {
        Date dateIni = fechaIni;
        Date dateFin = fehcaFin;
        boolean state = true;
//        System.out.println("validacion" + fechaIni);
//        System.out.println("validacion" + fehcaFin);

        if (dateIni != null && dateFin != null) {
            Calendar fecIni = Calendar.getInstance();
            Calendar fecFin = Calendar.getInstance();
            fecIni.setTime(dateIni);
            fecFin.setTime(dateFin);
            int diffAnio = (fecIni.get(Calendar.YEAR)) - fecFin.get(Calendar.YEAR);
            int diffMes = fecIni.get(Calendar.MONTH) - fecFin.get(Calendar.MONTH);
            int diffDia = fecIni.get(Calendar.DAY_OF_MONTH) - fecFin.get(Calendar.DAY_OF_MONTH);
            //verifica si la fecha ingresada no es neor que la actual
            if (diffAnio > 0 || diffMes > 0 || diffDia > 0) {

                state = true;

//                System.out.println("validacionerror" + state);
            } else {

                state = false;
            }
        } else {
            state = true;
        }


//        System.out.println("validacion" + state);
        return state;
    }

    /***
     * permite validar si una fecha es igual o posterior a la otra
     * @param fechaIni
     * @param fehcaFin
     */
    public static boolean validateEqualsAfterDate(Date fechaIni, Date fehcaFin) {
        Date dateIni = fechaIni;
        Date dateFin = fehcaFin;
        boolean state = false;
//        System.out.println("validacion" + fechaIni);
//        System.out.println("validacion" + fehcaFin);

        if (dateIni != null && dateFin != null) {
            Calendar fecIni = Calendar.getInstance();
            Calendar fecFin = Calendar.getInstance();
            fecIni.setTime(dateIni);
            fecFin.setTime(dateFin);

            String strfecIni = "" + (fecIni.get(Calendar.YEAR)) + "-" + fecIni.get(Calendar.MONTH) + "-" + fecIni.get(Calendar.DAY_OF_MONTH);

            String strfecFin = "" + (fecFin.get(Calendar.YEAR)) + "-" + fecFin.get(Calendar.MONTH) + "-" + fecFin.get(Calendar.DAY_OF_MONTH);
            if (dateFin.after(dateIni) | strfecIni.equalsIgnoreCase(strfecFin)) {

                state = true;

//                System.out.println("EqualsAfter" + state);
            } else {

                state = false;



//                System.out.println(" no EqualsAfter" + state);

            }

        }

          return state;

        }


}

