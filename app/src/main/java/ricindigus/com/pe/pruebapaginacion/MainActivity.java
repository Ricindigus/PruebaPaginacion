package ricindigus.com.pe.pruebapaginacion;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Persona> personas = new ArrayList<>();
    PersonaAdapter personaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargaData();
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        personaAdapter = new PersonaAdapter(recyclerView,this,personas);
        recyclerView.setAdapter(personaAdapter);
        personaAdapter.setLoadMore(new LoadMore() {
            @Override
            public void onLoadMore() {
                if (personas.size() <=50){
                    personas.add(null);
                    personaAdapter.notifyItemInserted(personas.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            personas.remove(personas.size()-1);
                            personaAdapter.notifyItemRemoved(personas.size()-1);

                            personas.add(new Persona("Fretman",35));
                            personas.add(new Persona("Fretman",35));
                            personas.add(new Persona("Fretman",35));
                            personas.add(new Persona("Fretman",35));
                            personas.add(new Persona("Fretman",35));
                            personas.add(new Persona("Fretman",35));
                            personas.add(new Persona("Fretman",35));
                            personas.add(new Persona("Fretman",35));
                            personas.add(new Persona("Fretman",35));
                            personas.add(new Persona("Fretman",35));

                            personaAdapter.notifyDataSetChanged();
                            personaAdapter.setLoaded();
                        }
                    },5000);
                }else {
                    Toast.makeText(MainActivity.this, "Load data completed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cargaData() {
        personas.add(new Persona("Ricardo",33));
        personas.add(new Persona("Ricardo",33));
        personas.add(new Persona("Ricardo",33));
        personas.add(new Persona("Ricardo",33));
        personas.add(new Persona("Ricardo",33));
        personas.add(new Persona("Ricardo",33));
        personas.add(new Persona("Ricardo",33));
        personas.add(new Persona("Ricardo",33));
        personas.add(new Persona("Ricardo",33));
        personas.add(new Persona("Ricardo",33));
        personas.add(new Persona("Ricardo",33));
    }
}
