package mx.androidtitlan.GDGHackup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class HomeActivity extends Activity {

	private Button button;
	private final int flag = 1;
	private ImageView imageView;
	private Bitmap imagenCapturada;
	private File file;
	private String date , lat, lon ,referencelat, referencelon,tipodeincidente="";
	private Float Latitude , Longitude;
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		this.button = (Button) findViewById(R.id.tomar_foto);
		imageView = (ImageView) findViewById(R.id.imagen_capturada);
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tomarFoto(flag);
			}
		});
		
  
	}

	private void tomarFoto(int flag) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		this.file = new File(Environment.getExternalStorageDirectory(),
				"imagen.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

		startActivityForResult(intent, flag);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		AlertDialog dialog = null;
		Uri uri = null;

		if (resultCode == RESULT_CANCELED) {

			dialog = crearDialogo("FOTO", "Fallo al tomar la foto", "OK");
			dialog.show();

		} else {

			uri = data.getData();
			try {
				imagenCapturada = Media.getBitmap(this.getContentResolver(),
						uri);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// imagenCapturada = (Bitmap) data.getExtras().get("data");
			dialog = crearDialogo("FOTO", "EXITO", "OK");
			dialog.show();

		}

	}

	private AlertDialog crearDialogo(String title, String msg, String buttonText) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		AlertDialog msgDialog = dialogBuilder.create();
		msgDialog.setTitle(title);
		msgDialog.setMessage(msg);
		msgDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int idx) {
				dialog.dismiss();
				imageView.setImageBitmap(imagenCapturada);
				try {
					ExifInterface exifInterface = new ExifInterface(Environment
							.getExternalStorageDirectory() + "/imagen.jpg");
					 				
					date = exifInterface.getAttribute(ExifInterface.TAG_DATETIME); //fecha
					lat = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE); //latitud
					referencelat = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF); //referencia latitud
					lon = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE); //longitud
					referencelon = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF); //referencia longitud
					
					
					if(referencelat.equals("N")){
						 Latitude = convertToDegree(lat); //hacemos el metodo convertToDegree para convertir los valores que estan fracciones
						 						  }
						  else{
						   Latitude = 0 - convertToDegree(lat);
						  }

						  if(referencelon.equals("E")){
						   Longitude = convertToDegree(lon);
						  }
						  else{
						   Longitude = 0 - convertToDegree(lon);
						  }

						 
				   
					
					Log.i("TAG_DATE",date);
					Log.i("TAG_LATITUD",""+Latitude);
					Log.i("TAG_LATITUD_REF",referencelat);
					Log.i("TAG_LONGITUD",""+Longitude);
					Log.i("TAG_LONGITUD_REF",referencelon);
					
					
					

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			private Float convertToDegree(String stringDMS) {

				Float result = null;
				 String[] DMS = stringDMS.split(",", 3);

				 String[] stringD = DMS[0].split("/", 2);
				    Double D0 = new Double(stringD[0]);
				    Double D1 = new Double(stringD[1]);
				    Double FloatD = D0/D1;

				 String[] stringM = DMS[1].split("/", 2);
				 Double M0 = new Double(stringM[0]);
				 Double M1 = new Double(stringM[1]);
				 Double FloatM = M0/M1;

				 String[] stringS = DMS[2].split("/", 2);
				 Double S0 = new Double(stringS[0]);
				 Double S1 = new Double(stringS[1]);
				 Double FloatS = S0/S1;

				    result = new Float(FloatD + (FloatM/60) + (FloatS/3600));

				 return result;

				
			}

		});

		return msgDialog;
	}
	
	
	public void Choque (View v){
		
		String cadenaimagen = imageView.toString();
		String enviarLat = Latitude.toString();
		String enviarLon = Longitude.toString();
		tipodeincidente="choque";

		new Postt(date,tipodeincidente, enviarLat, enviarLon, cadenaimagen).execute();
		Toast.makeText(this, "se envio", Toast.LENGTH_LONG).show();
	     
		}	
	
	
	
public void Lugar (View v){
		
		String cadenaimagen = imageView.toString();
		String enviarLat = Latitude.toString();
		String enviarLon = Longitude.toString();
		tipodeincidente="Lugar prohibido";

		new Postt(date,tipodeincidente, enviarLat, enviarLon, cadenaimagen).execute();
		Toast.makeText(this, "se envio", Toast.LENGTH_LONG).show();
	     
		}	

public void Atropeyamiento (View v){
	
	String cadenaimagen = imageView.toString();
	String enviarLat = Latitude.toString();
	String enviarLon = Longitude.toString();
	tipodeincidente="Atropeyamiento";

	new Postt(date,tipodeincidente, enviarLat, enviarLon, cadenaimagen).execute();
	Toast.makeText(this, "se envio", Toast.LENGTH_LONG).show();
     
	}	
	
	}
	



