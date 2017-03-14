package es.jco.examenandroidflp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CODE1 = 1;
    private static final int CODE2 = 2;
    private ListView lista;

    private List<Usuario> listausuarios = new ArrayList<>();

    private AdaptadorUsuarios adaptador;

    private static int id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(i, CODE1);
            }
        });

        lista = (ListView)findViewById(R.id.lista);

        cargarDatos();

        adaptador = new AdaptadorUsuarios(this);
        lista.setAdapter(adaptador);

        registerForContextMenu(lista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        LayoutInflater inflate = getLayoutInflater();
        View item = inflate.inflate(R.layout.header, null);
        TextView txtheader = (TextView) item.findViewById(R.id.txtheader);
        txtheader.setText("Título");
        menu.setHeaderView(item);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();
        id = info.position;
        switch (item.getItemId()) {
            case R.id.opcDel:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.tituloDialogo));

                builder.setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listausuarios.remove(info.position);
                        adaptador.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CODE1:
                if (resultCode == RESULT_OK) {
                    Usuario u = (Usuario) data.getSerializableExtra("usuario");
                    u.setId(listausuarios.size()+1);
                    listausuarios.add(u);

                    adaptador.notifyDataSetChanged();
                }
                break;
            case CODE2:
                if (resultCode == RESULT_OK) {
                    Usuario u = (Usuario) data.getSerializableExtra("usuario");
                    listausuarios.set(id, u);
                    adaptador.notifyDataSetChanged();
                }
                break;
        }
    }

    private void cargarDatos() {
        listausuarios.add(new Usuario(1, 0, "Antonio Pérez", "antonioprz@gmail.com"));
        listausuarios.add(new Usuario(2, 1, "Juan Arroyo", "juanarroyo@gmail.com"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    class AdaptadorUsuarios extends ArrayAdapter {

        Activity context;

        public AdaptadorUsuarios(Activity context) {
            super(context, R.layout.fila_usuario, listausuarios);
            this.context = context;
        }

        //@NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;

            ViewHolder holder;

            if (item == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.fila_usuario, null);

                holder = new ViewHolder();
                holder.txtNombre = (TextView) item.findViewById(R.id.txtNombre);
                holder.txtEmail = (TextView) item.findViewById(R.id.txtEmail);
                holder.imagen = (ImageView) item.findViewById(R.id.imagen);

                item.setTag(holder);
            } else {
                holder = (ViewHolder)item.getTag();
            }

            holder.txtNombre.setText(listausuarios.get(position).getNombre());
            holder.txtEmail.setText(String.valueOf(listausuarios.get(position).getEmail()));
            switch (listausuarios.get(position).getTipo()) {
                case 0:
                    holder.imagen.setBackgroundResource(R.mipmap.ic_empleado);
                    break;
                case 1:
                    holder.imagen.setBackgroundResource(R.mipmap.ic_directivo);
                    break;
            }


            return item;
        }
    }

    static class ViewHolder {
        TextView txtNombre;
        TextView txtEmail;
        ImageView imagen;
    }
}
