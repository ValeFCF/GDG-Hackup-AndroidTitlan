package mx.androidtitlan.GDGHackup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;


public class Postt extends AsyncTask<String, Void, Void>{
	
	String date,lat,lon,cadima,tipoincidente ;

	public Postt(String fecha, String tipoin, String latit, String longi, String cadeimage){
		date=fecha;
		lat=latit;
		lon=longi;
		cadima=cadeimage;
		tipoincidente = tipoin;
		
		}
	
	
	 
	@Override
	protected Void doInBackground(String... params) {
		
		
			 HttpClient httpclient = new DefaultHttpClient(); //Objeto Cliente  
//		     HttpPost httppost = new HttpPost("192.168.1.72:8080/newreport");
			 HttpPost httppost = new HttpPost("http://requestb.in/ydkt22yd");
		    
		 
		     try {  
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4); 
			        nameValuePairs.add(new BasicNameValuePair("datetime", date));
			        nameValuePairs.add(new BasicNameValuePair("tipo de incidente", tipoincidente));
			        nameValuePairs.add(new BasicNameValuePair("latitude", lat));  
			        nameValuePairs.add(new BasicNameValuePair("longitude", lon));
			        nameValuePairs.add(new BasicNameValuePair("imagen", cadima));
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); //Cargamos los valores  
			        httpclient.execute(httppost); 
			        
			      } catch (ClientProtocolException e) {  
			          // TODO Auto-generated catch block  
			      } catch (IOException e) {  
			          // TODO Auto-generated catch block  
			      }  
		

		// TODO Auto-generated method stub
		return null;
	
	}
	
	
	protected void onProgressUpdate(String...progress ){
		
	}
	
	protected void onPostExecute(){
		
		
	}




	
	
	
}
