package com.apmv1.loginpref;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class PrefLogin extends AppCompatActivity {

    private static final int LOGIN_ACTION = 0;
    private static final int SETTINGS_ACTION = 1;
    private boolean validUser = false;
    private String userNamePreferences;
    private String passwordPreferences;

    private void showAccessPopUpDialog(final String userName, final String password, final int action){
        final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Acceso a clientes");
        helpBuilder.setMessage("Introduzca usario y password");
        helpBuilder.setIcon(R.drawable.key_stroke_32x32);

        //Creamos un TextView para la etiqueta Usuario
        final TextView LabelName = new TextView(this);
        LabelName.setWidth(160);
        LabelName.setText("Usuario:");

        //Creamos un EditText para teclear el valor del campo Usuario
        final EditText inputName = new EditText(this);
        inputName.setSingleLine();
        inputName.setWidth(200);
        inputName.setInputType(InputType.TYPE_CLASS_TEXT);
        inputName.setText("");

        //Creamos un TextView para la etiqueta Contraseña
        final TextView LabelPass = new TextView(this);
        LabelPass.setWidth(160);
        LabelPass.setText("Password:");

        //Creamos un EditText para teclear el valor del campo Contraseña
        final EditText inputPass = new EditText(this);
        inputPass.setSingleLine();
        inputPass.setWidth(200);
        inputPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPass.setText("");

        //Definimos parametros para luego aplicar al Layout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        //Creamos un Layout comun para agrupar la entiqueta(TexView) y el campo (EditText)
        //Tanto del Usuario como de la Contraseña
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setPadding(10, 5, 0, 5);

        //Creamos un Layout para agrupar la entiqueta(TexView) y el campo (EditText)
        // del Usuario
        LinearLayout layoutUser = new LinearLayout(this);
        // Aplicamos los parámetros del layout a nuestros objectos
        layoutUser.setLayoutParams(params);
        layoutUser.addView(LabelName);
        layoutUser.addView(inputName);

        //Creamos un Layout para agrupar la entiqueta(TexView) y el campo (EditText)
        //de la Contraseña
        LinearLayout layoutPass = new LinearLayout(this);
        // Aplicamos los parámetros del layout a nuestros objectos
        layoutPass.setLayoutParams(params);
        layoutPass.addView(LabelPass);
        layoutPass.addView(inputPass);

        //Adicionamos al Layout comun los dos nuevos layouts
        // el del Usuario y el de la Contraseña, creados anteriormente
        layout.addView(layoutUser);
        layout.addView(layoutPass);

        //Adicionamos el layout comun al helpBuilder
        helpBuilder.setView(layout);

        helpBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                String inputUserTxt = inputName.getText().toString();
                String inputPasswordTxt = inputPass.getText().toString();

                if (inputUserTxt.equals(userName) & inputPasswordTxt.equals(password)) {
                    //Seleccionamos la acción a ejecutar
                    loginAction(action);
                } else {
                    Toast toast = Toast.makeText(getBaseContext(),
                            "Usuario o Contraseña no válidos. Inténtelo otra vez", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
// Si la contraseña o el usuario no son válidos volvemos a motrar el diálogo
                    showAccessPopUpDialog(userNamePreferences, passwordPreferences, action);
                }
            }
        });



        helpBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                finish();
            }
        });

        // Crear el diálogo no lo muestra, hay que hacer el show()
        AlertDialog helpDialog = helpBuilder.create();
        // Deshabilitar hacer click fuera del diálogo
        helpDialog.setCanceledOnTouchOutside(false);
        helpDialog.setTitle("Ingrese sus Credenciales");

        //Mostrar el diálogo
        helpDialog.show();
        //Establecer ancho y alto
        helpDialog.getWindow().setLayout(900, 950);

    }

    protected boolean loginAction(int action){
        switch (action) {
            case LOGIN_ACTION:
                validUser = true;
                Toast toast = Toast.makeText(getBaseContext(), "USUARIO Y CONTRASEÑA CORRECTOS. (:", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return true;
            case SETTINGS_ACTION:
//Preferences access
                startActivity(new Intent(getApplicationContext(), Preferences.class));
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_login);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(!validUser){
            userNamePreferences = Preferences.getUsernameSettingsValue(this);
            passwordPreferences = Preferences.getPasswordSettingsValue(this);
            showAccessPopUpDialog(userNamePreferences, passwordPreferences, LOGIN_ACTION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pref_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
