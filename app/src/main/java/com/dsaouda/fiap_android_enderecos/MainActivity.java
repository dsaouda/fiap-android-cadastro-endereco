package com.dsaouda.fiap_android_enderecos;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.dsaouda.fiap_android_enderecos.view.adapter.EnderecoListaAdapter;
import com.dsaouda.fiap_android_enderecos.model.Endereco;
import com.dsaouda.fiap_android_enderecos.repository.EnderecoRepository;
import com.dsaouda.fiap_android_enderecos.view.holder.EnderecoViewHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActiveAndroid.initialize(this);

        recycleViewEnderecoLista();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(MainActivity.this, EnderecoActivity.class);
                startActivityForResult(intent, 200);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void openChrome(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            intent.setPackage(null);
            this.startActivity(intent);
        }
    }

    private void recycleViewEnderecoLista() {
        final List<Endereco> enderecos = new EnderecoRepository().buscarTodos();

        RecyclerView rvEnderecoLista = (RecyclerView) findViewById(R.id.rvEnderecoLista);
        TextView tvSemEndereco = (TextView) findViewById(R.id.tvSemEndereco);

        if (enderecos.size() == 0) {
            tvSemEndereco.setVisibility(View.VISIBLE);
            rvEnderecoLista.setVisibility(View.INVISIBLE);
        } else {
            tvSemEndereco.setVisibility(View.INVISIBLE);
            rvEnderecoLista.setVisibility(View.VISIBLE);
        }

        final EnderecoListaAdapter adapter = new EnderecoListaAdapter(enderecos, this);

        rvEnderecoLista.setLayoutManager(new LinearLayoutManager(this));
        rvEnderecoLista.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        rvEnderecoLista.invalidate();

        //swipe
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Snackbar.make(viewHolder.itemView, "Deletado", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                ((EnderecoViewHolder)viewHolder).removerEndereco();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvEnderecoLista);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_CANCELED:
                Toast.makeText(MainActivity.this, "Cancelado", Toast.LENGTH_LONG).show();
                break;

            case 201:
                Toast.makeText(MainActivity.this, "Endereço salvo com sucesso", Toast.LENGTH_LONG).show();
                recycleViewEnderecoLista();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_fechar) {
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_github:
                openChrome("https://github.com/dsaouda/fiap-android-cadastro-endereco");
                break;

            case R.id.nav_sobre_via_cep:
                openChrome("https://viacep.com.br");
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
