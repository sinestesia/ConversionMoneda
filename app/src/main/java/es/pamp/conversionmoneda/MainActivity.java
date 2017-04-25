package es.pamp.conversionmoneda;

        import android.os.AsyncTask;
        import android.os.StrictMode;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RadioGroup;
        import android.widget.TextView;

        import org.ksoap2.SoapEnvelope;
        import org.ksoap2.serialization.PropertyInfo;
        import org.ksoap2.serialization.SoapObject;
        import org.ksoap2.serialization.SoapPrimitive;
        import org.ksoap2.serialization.SoapSerializationEnvelope;
        import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {
    private EditText cantidadET;
    private TextView resultadoTV;
    private TextView valorTV;
    private String moneda;
    private RadioGroup selectorCambioRG;
    private RadioGroup selectorMonedaRG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cantidadET = (EditText) findViewById(R.id.cantidadET);
        selectorCambioRG = (RadioGroup) findViewById(R.id.selectorCambioRG);
        selectorMonedaRG = (RadioGroup) findViewById(R.id.selectorMonedaRG);

        Button calcularBoton = (Button) findViewById(R.id.calcularBoton);
        calcularBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (selectorMonedaRG.getCheckedRadioButtonId()) {
                    case R.id.dolarRB:
                        moneda = "DOLAR";
                        break;
                    case R.id.pesoRB:
                        moneda = "PESOS";
                        break;
                    case R.id.rupiaRB:
                        moneda = "RUPIAS";
                        break;
                    case R.id.yenRB:
                        moneda = "YENES";
                        break;
                    case R.id.pesetaRB:
                        moneda = "PESETAS";
                        break;
                    default:
                        moneda = "";
                }

                if (selectorCambioRG.getCheckedRadioButtonId() == R.id.meRB){
                    calcularCambioEM(moneda);
                }
                if (selectorCambioRG.getCheckedRadioButtonId() == R.id.emRB){
                    calcularCambio(moneda);
                }

            }
        });

    }

    public void calcularCambioEM(String moneda){

        new PeticionAsincronaEM().execute(cantidadET.getText().toString(), moneda);

    }

    private class PeticionAsincronaEM extends AsyncTask<Object, Void, String > {
        private String SOAPACTION = "http://ConversionMoneda/monedaEuro";
        private  String METHOD = "monedaEuro";
        private  String NAMESPACE = "http://ConversionMoneda/";
        private  String URL = "http://10.1.2.108:8084/ConversionMoneda/ConversionMonedaService?WSDL";

        @Override
        protected String doInBackground(Object... params) {
            String resultadoFinal = null;
             /*Lo recomendado es crear esa tarea en un subproceso o hilo secundario,
                no obstante, si necesitáis hacerlo a la fuerza, se puede establecer un cambio en las políticas de restricciones
                de Android para nuestra clase (repito, no es recomendable). Lo único que habría que hacer es insertar estas dos líneas
                de código en el onCreate() de nuestra clase principal, y Android se tragará cualquier acceso a red que hagamos en el Main Thread, sin rechistar */

            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
            //StrictMode.setThreadPolicy(policy);


            String resultadoFINAL;

            //Creacion de la Solicitud
            SoapObject request = new SoapObject(NAMESPACE, METHOD);
            // Creacion del Envelope
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.setOutputSoapObject(request);
            //Creacion del transporte
            HttpTransportSE transporte = new HttpTransportSE(URL);

            // Paso de parámetro
            PropertyInfo cantidad = new PropertyInfo();
            cantidad.setName("cantidad");
            cantidad.setValue(params[0]);// solo admite string, int y boolean
            //cantidad.setValue("170.3");
            cantidad.setType(Double.class);
            request.addProperty(cantidad);

            // Paso de parámetro
            PropertyInfo moneda = new PropertyInfo();
            moneda.setName("moneda");
            moneda.setValue(params[1]);
            //moneda.setValue("DOLAR");
            moneda.setType(String.class);
            request.addProperty(moneda);

            try {

                //Llamada
                transporte.call(SOAPACTION, sobre);
                //Resultado
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                resultadoFinal = resultado.toString();
            }
            catch (Exception e) {}

            return resultadoFinal;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            resultadoTV = (TextView) findViewById(R.id.resultadoTV);
            resultadoTV.setText(s.toString());

        }
    }

    public void calcularCambio(String moneda){

        new PeticionAsincrona().execute(cantidadET.getText().toString(), "DOLAR");

    }

    private class PeticionAsincrona extends AsyncTask<Object, Void, String > {

        private String SOAPACTION = "http://ConversionMoneda/euroMoneda";
        private String METHOD = "euroMoneda";
        private String NAMESPACE = "http://ConversionMoneda/";
        private String URL = "http://10.1.2.108:8084/ConversionMoneda/ConversionMonedaService?WSDL";

        @Override
        protected String doInBackground(Object... params) {
            String resultadoFinal = null;
             /*Lo recomendado es crear esa tarea en un subproceso o hilo secundario,
                no obstante, si necesitáis hacerlo a la fuerza, se puede establecer un cambio en las políticas de restricciones
                de Android para nuestra clase (repito, no es recomendable). Lo único que habría que hacer es insertar estas dos líneas
                de código en el onCreate() de nuestra clase principal, y Android se tragará cualquier acceso a red que hagamos en el Main Thread, sin rechistar */

            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
            //StrictMode.setThreadPolicy(policy);


            String resultadoFINAL;

            //Creacion de la Solicitud
            SoapObject request = new SoapObject(NAMESPACE, METHOD);
            // Creacion del Envelope
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.setOutputSoapObject(request);
            //Creacion del transporte
            HttpTransportSE transporte = new HttpTransportSE(URL);

            // Paso de parámetro
            PropertyInfo cantidad = new PropertyInfo();
            cantidad.setName("cantidad");
            cantidad.setValue(params[0]);// solo admite string, int y boolean
            //cantidad.setValue("170.3");
            cantidad.setType(Double.class);
            request.addProperty(cantidad);

            // Paso de parámetro
            PropertyInfo moneda = new PropertyInfo();
            moneda.setName("moneda");
            moneda.setValue(params[1]);
            //moneda.setValue("DOLAR");
            moneda.setType(String.class);
            request.addProperty(moneda);

            try {

                //Llamada
                transporte.call(SOAPACTION, sobre);
                //Resultado
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                resultadoFinal = resultado.toString();
            }
            catch (Exception e) {}

            return resultadoFinal;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            resultadoTV = (TextView) findViewById(R.id.resultadoTV);
            resultadoTV.setText(s.toString());

        }
    }

}


/*
*  CODIGo DEL SERVICIO
*
*
*
*  package ConversionMoneda;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import static jdk.nashorn.internal.objects.NativeMath.round;


@WebService(serviceName = "ConversionMonedaService")
public class ConversionMonedaService {


    @WebMethod(operationName = "euroMoneda")
    public double euroMoneda(@WebParam(name = "cantidad") double cantidad, @WebParam(name = "moneda") String moneda) {
        double valor=0;

        switch (moneda) {
            case "DOLAR":
                valor= cantidad * 1.26201;
                break;
            case "PESOS":
                valor= cantidad * 14.4762;
                break;
            case "RUPIAS":
                valor= cantidad * 75.6061;
                break;
            case "YENES":
                valor= cantidad * 135.861;
                break;
            case "PESETAS":
                valor= cantidad * 166.386;
                break;
            default:
                valor=0;
        }

        return Math.round(valor * 100.0) / 100.0;
    }
    @WebMethod(operationName = "monedaEuro")
    public double monedaEuro(@WebParam(name = "cantidad") double cantidad, @WebParam(name = "moneda") String moneda) {
        double valor=0;

        switch (moneda) {
            case "DOLAR":
                valor= cantidad / 1.26201;
                break;
            case "PESOS":
                valor= cantidad / 14.4762;
                break;
            case "RUPIAS":
                valor= cantidad / 75.6061;
                break;
            case "YENES":
                valor= cantidad / 135.861;
                break;
            case "PESETAS":
                valor= cantidad / 166.386;
                break;
            default:
                valor=0;
        }

        return Math.round(valor * 100.0) / 100.0;
    }
}
*
*
*
*
*
*
* */