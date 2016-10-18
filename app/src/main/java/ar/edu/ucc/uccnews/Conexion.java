package ar.edu.ucc.uccnews;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by frans on 24/9/2016.
 */
public class Conexion {

    private Context ctx;

    public Conexion(Context contexto)
    {
        ctx = contexto;
        comprobar();

    }

private boolean flag;


    ///////////////////////////////////////////////////////7
    public boolean consultar(String user , String pass)
    {
        hacerRequest(user,pass);
        return flag;
    }


    private void comprobar()
    {
        if (!infoNet())
        {
            Toast.makeText(ctx,"Compruebe su  conexion de red ",Toast.LENGTH_SHORT).show();
        }

    }
    private Boolean infoNet()
    {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com.ar");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void hacerRequest(String Usuario, String Contraseña) {
        // URL del pedido
        String url = "http://www.marku.me:1337/api/usuario/login";

        // DAtos para el POST
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("user", Usuario);
            jsonBody.put("password", Contraseña);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue rq = Volley.newRequestQueue(ctx);

        // Inicio el POST
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonBody
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    String pedido = response.getString("status");
                    //Toast.makeText(contexto,pedido,Toast.LENGTH_LONG).show();
                    Log.d("UCC", response.getString("status"));

                    //Comprueba el que los datos ingresados sean correcttos
                    if (pedido.equals("ok"))
                        flag=true;

                    else
                        flag=false;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(ctx,"\t Error 408 \nRequest Timeout",Toast.LENGTH_LONG).show();
                        Log.e("VOLLEY", "ERROR");
                    }
                }
        );

        rq.add(jsonObjectRequest);



    }

}
