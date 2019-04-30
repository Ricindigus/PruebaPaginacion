package ricindigus.com.pe.pruebapaginacion;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class PersonaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_PERSONA = 0, VIEW_TYPE_LOADING = 1;
    LoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Persona> personas;
    int visibleTreshold = 5;
    int lastVisibleItem, totalItemCount;


    public PersonaAdapter(RecyclerView recyclerView, Activity activity, List<Persona> personas) {
        this.activity = activity;
        this.personas = personas;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition() + 1;
                if (!isLoading && totalItemCount<=(lastVisibleItem)){
                    if (loadMore != null){
                        loadMore.onLoadMore();
                    }
                    isLoading = true;
                }

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return personas.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_PERSONA;
    }

    public void setLoadMore(LoadMore loadMore) {
        this.loadMore = loadMore;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_PERSONA){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_persona,viewGroup,false);
            return new PersonaHolder(view);
        }else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading,viewGroup,false);
            return new LoadingHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof PersonaHolder){
            Persona currentPersona = personas.get(position);
            PersonaHolder personaHolder = (PersonaHolder) viewHolder;
            personaHolder.txtNombre.setText(currentPersona.getNombre());
            personaHolder.txtEdad.setText(currentPersona.getEdad()+"");
        }else if (viewHolder instanceof LoadingHolder){
            LoadingHolder loadingHolder = (LoadingHolder) viewHolder;
            loadingHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return personas.size();
    }

    public static class LoadingHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    public static class PersonaHolder extends RecyclerView.ViewHolder {
        TextView txtNombre;
        TextView txtEdad;
        public PersonaHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtEdad = itemView.findViewById(R.id.txtEdad);
        }
    }
}
