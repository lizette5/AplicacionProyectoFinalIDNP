    package com.example.aplicacion.Presenter.Servicios;

    import android.Manifest;
    import android.annotation.SuppressLint;
    import android.app.Service;
    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.graphics.Color;
    import android.location.Location;
    import android.location.LocationListener;
    import android.location.LocationManager;
    import android.os.Bundle;
    import android.os.IBinder;
    import android.util.Log;
    import android.view.View;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.core.app.ActivityCompat;

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.JsonObjectRequest;
    import com.android.volley.toolbox.Volley;
    import com.example.aplicacion.MapsFragment;
    import com.example.aplicacion.View.Utilidades;
    import com.google.android.gms.maps.CameraUpdate;
    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.model.LatLng;
    import com.google.android.gms.maps.model.MarkerOptions;
    import com.google.android.gms.maps.model.PolylineOptions;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    public class MiServicioGPS extends Service implements LocationListener {
        private final Context contex;
        public double longitud;
        public double latitud;
        //public Location location;
        boolean gpsActivo;
        Location location;
        LocationManager locationManager;


        boolean flag1 = true;
        double latinicial, longinicial;
        float distance, totalDistance=0;
        Location locationB = new Location("punto B");//llega

        JsonObjectRequest jsonObjectRequest;
        RequestQueue request;
        int i =0;


        public GoogleMap mMap = null;
        public TextView texto = null;

        public MiServicioGPS() {
            super();
            this.contex = getApplicationContext();
        }

        public MiServicioGPS(Context contex) {
            super();
            this.contex = contex;
            //request= Volley.newRequestQueue(contex);
            getLocation();

        }

        @SuppressLint("MissingPermission")
        public void getLocation() {
            try {
                locationManager = (LocationManager) this.contex.getSystemService(LOCATION_SERVICE);
                gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception e) {
            }if (gpsActivo) {
                /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }*/
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER
                        , 1000 * 10
                        , 5, this);//peticion al servicio para obtener actualizaciones del gps cada cierto tiempo
                location  =  locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

                latinicial = location.getLatitude();
                longinicial = location.getLongitude();

            }

        }
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {

            //preparar();
            Location locationA = new Location("punto A");//tengo
            locationA.setLatitude( latinicial);

            locationA.setLongitude(longinicial);

            locationB.setLatitude(location.getLatitude());
            locationB.setLongitude(location.getLongitude());
            //Toast.makeText(contex, "Coordenadas: "+location.getLatitude() + " / "+ "-16.381587134590085", Toast.LENGTH_LONG ).show();
            distance= locationB.distanceTo(locationA);
            locationB.reset();
            Toast.makeText(contex, "Coordenadas: "+location.getLatitude() + " / "+ location.getLongitude()+ "**"+ ++i+ "Distance: "+ distance, Toast.LENGTH_LONG ).show();

            latinicial = location.getLatitude();
            longinicial = location.getLongitude();
            totalDistance+=distance;
            if(texto!=null){
                texto.setText(totalDistance+" mts");
            }
            if(this.mMap !=null){
                LatLng  latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }


            //LatLng tmp = new LatLng( location.getLatitude(),location.getLongitude());
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(tmp));

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
        public void setView(View view){
            texto = (TextView)view;
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){

        }
        public void preparar(){
            Location locationA = new Location("punto A");//tengo
            locationA.setLatitude( -16.381587134590085);

            locationA.setLongitude(-71.51251244752586);
            locationB.setLatitude(location.getLatitude());
            locationB.setLongitude(location.getLongitude());
            Toast.makeText(contex, "Coordenadas: "+location.getLatitude() + " / "+ "-16.381587134590085", Toast.LENGTH_LONG ).show();
            distance= locationB.distanceTo(locationA);
            locationB.reset();
            //latinicial= location.getLatitude();
            //longinicial = location.getLongitude();
        }

        public void setMap(GoogleMap a){
            mMap = a;
        }

       /* private void webServiceObtenerRuta(String latitudInicial, String longitudInicial, String latitudFinal, String longitudFinal) {

            String url="https://maps.googleapis.com/maps/api/directions/json?origin="+latitudInicial+","+longitudInicial
                    +"&destination="+latitudFinal+","+longitudFinal;

            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //Este método PARSEA el JSONObject que retorna del API de Rutas de Google devolviendo
                    //una lista del lista de HashMap Strings con el listado de Coordenadas de Lat y Long,
                    //con la cual se podrá dibujar pollinas que describan la ruta entre 2 puntos.
                    JSONArray jRoutes = null;
                    JSONArray jLegs = null;
                    JSONArray jSteps = null;

                    try {

                        jRoutes = response.getJSONArray("routes");

                        /** Traversing all routes *//*
                        for(int i=0;i<jRoutes.length();i++){
                            jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                            List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                            /** Traversing all legs *//*
                            for(int j=0;j<jLegs.length();j++){
                                jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                                /** Traversing all steps *//*
                                for(int k=0;k<jSteps.length();k++){
                                    String polyline = "";
                                    polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                                    List<LatLng> list = decodePoly(polyline);

                                    /** Traversing all points *//*
                                    for(int l=0;l<list.size();l++){
                                        HashMap<String, String> hm = new HashMap<String, String>();
                                        hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                                        hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                                        path.add(hm);
                                    }
                                }
                                Utilidades.routes.add(path);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (Exception e){
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(contex.getApplicationContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                    System.out.println();
                    Log.d("ERROR: ", error.toString());
                }
            }
            );

            request.add(jsonObjectRequest);
        }
    /*
        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }

            return poly;
        }
    /*
        public void draw(){


            LatLng center = null;
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // setUpMapIfNeeded();

            // recorriendo todas las rutas
            for(int i = 0; i< Utilidades.routes.size(); i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Obteniendo el detalle de la ruta
                List<HashMap<String, String>> path = Utilidades.routes.get(i);

                // Obteniendo todos los puntos y/o coordenadas de la ruta
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    if (center == null) {
                        //Obtengo la 1ra coordenada para centrar el mapa en la misma.
                        center = new LatLng(lat, lng);
                    }
                    points.add(position);
                }

                // Agregamos todos los puntos en la ruta al objeto LineOptions
                lineOptions.addAll(points);
                //Definimos el grosor de las Polilíneas
                lineOptions.width(2);
                //Definimos el color de la Polilíneas
                lineOptions.color(Color.RED);
            }

            // Dibujamos las Polilineas en el Google Map para cada ruta
            mMap.addPolyline(lineOptions);

            LatLng origen = new LatLng(Utilidades.coordenadas.getLatitudInicial(), Utilidades.coordenadas.getLongitudInicial());
            mMap.addMarker(new MarkerOptions().position(origen).title("Lat: "+Utilidades.coordenadas.getLatitudInicial()+" - Long: "+Utilidades.coordenadas.getLongitudInicial()));

            LatLng destino = new LatLng(Utilidades.coordenadas.getLatitudFinal(), Utilidades.coordenadas.getLongitudFinal());
            mMap.addMarker(new MarkerOptions().position(destino).title("Lat: "+Utilidades.coordenadas.getLatitudFinal()+" - Long: "+Utilidades.coordenadas.getLongitudFinal()));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 15));
            /////////////
        }*/
    /*
        public List<List<HashMap<String,String>>> parse(JSONObject jObject){
            //Este método PARSEA el JSONObject que retorna del API de Rutas de Google devolviendo
            //una lista del lista de HashMap Strings con el listado de Coordenadas de Lat y Long,
            //con la cual se podrá dibujar pollinas que describan la ruta entre 2 puntos.
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;

            try {

                jRoutes = jObject.getJSONArray("routes");

                /** Traversing all routes *//*
                for(int i=0;i<jRoutes.length();i++){
                    jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                    List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                    /** Traversing all legs *//*
                    for(int j=0;j<jLegs.length();j++){
                        jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                        /** Traversing all steps *//*
                        for(int k=0;k<jSteps.length();k++){
                            String polyline = "";
                            polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            /** Traversing all points *//*
                            for(int l=0;l<list.size();l++){
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                                hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                                path.add(hm);
                            }
                        }
                        Utilidades.routes.add(path);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
            }
            return Utilidades.routes;
        }
    */
    }
