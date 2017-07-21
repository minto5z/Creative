package ws.wolfsoft.creative;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.jinesh.loadingviews.LoadingImageView;
import test.jinesh.loadingviews.LoadingTextView;
import ws.wolfsoft.creative.model.Data;

public class ListActivity extends BaseActivity {


    public static final String EXTRA_TRANSITION = "EXTRA_TRANSITION";
    //    public static final String TRANSITION_FADE_FAST = "FADE_FAST";
//    public static final String TRANSITION_FADE_SLOW = "FADE_SLOW";
    public static final String TRANSITION_SLIDE_RIGHT = "SLIDE_RIGHT";
    public static final String TRANSITION_SLIDE_BOTTOM = "SLIDE_BOTTOM";
    public static final String TRANSITION_EXPLODE = "EXPLODE";
    public static final String TRANSITION_EXPLODE_BOUNCE = "EXPLODE_BOUNCE";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    ListView listView;
    ArrayList<Data> dataArrayList;
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);







        ButterKnife.bind(this);



        listView = (ListView) findViewById(R.id.list);
        dataArrayList = new ArrayList<>();
        prepareDummyDataForLoading();
        adapter = new Adapter();
        listView.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataArrayList.clear();
                prepareSomeDataForLoading();
                adapter.notifyDataSetChanged();
            }
        },4000);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
//                Intent intent = new Intent(ListActivity.this, SendMessage.class);
//                String message = "abc";
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);

                Toast.makeText(getApplicationContext(),"Clicked Position is "+ position,Toast.LENGTH_LONG).show();

                final AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle(" Dialog with List");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ListActivity.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("View");
                arrayAdapter.add("Edit");
                arrayAdapter.add("Delete");

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ListActivity.this, "CANCEL", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ListActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(ListActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
                builder.show();

            }
        });

        String transition = getIntent().getStringExtra(EXTRA_TRANSITION);
        switch (transition) {
            case TRANSITION_SLIDE_RIGHT:
                Transition transitionSlideRight =
                        TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
                getWindow().setEnterTransition(transitionSlideRight);
                break;
            case TRANSITION_SLIDE_BOTTOM:
                Transition transitionSlideBottom =
                        TransitionInflater.from(this).inflateTransition(R.transition.slide_bottom);
                getWindow().setEnterTransition(transitionSlideBottom);
                break;
//            case TRANSITION_FADE_FAST:
//                Transition transitionFadeFast =
//                        TransitionInflater.from(this).inflateTransition(R.transition.fade_fast);
//                getWindow().setEnterTransition(transitionFadeFast);
//                break;
//            case TRANSITION_FADE_SLOW:
//                Transition transitionFadeSlow =
//                        TransitionInflater.from(this).inflateTransition(R.transition.fade_slow);
//                getWindow().setEnterTransition(transitionFadeSlow);
//                break;
            case TRANSITION_EXPLODE:
                Transition transitionExplode =
                        TransitionInflater.from(this).inflateTransition(R.transition.explode);
                getWindow().setEnterTransition(transitionExplode);
                break;
            case TRANSITION_EXPLODE_BOUNCE:
                Transition transitionExplodeBounce =
                        TransitionInflater.from(this).inflateTransition(R.transition.explode_bounce);
                getWindow().setEnterTransition(transitionExplodeBounce);
                break;
        }

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @OnClick(R.id.text_close)
    public void onCloseTextClicked() {
        finishAfterTransition();
    }


    private void prepareDummyDataForLoading() {
        dataArrayList.add(new Data("", "", "", ContextCompat.getDrawable(this, R.drawable.cat_shocked), true));
        dataArrayList.add(new Data("", "", "", ContextCompat.getDrawable(this, R.drawable.cat_shocked), true));
        dataArrayList.add(new Data("", "", "", ContextCompat.getDrawable(this, R.drawable.cat_shocked), true));
        dataArrayList.add(new Data("", "", "", ContextCompat.getDrawable(this, R.drawable.cat_shocked), true));
        dataArrayList.add(new Data("", "", "", ContextCompat.getDrawable(this, R.drawable.cat_shocked), true));
        dataArrayList.add(new Data("", "", "", ContextCompat.getDrawable(this, R.drawable.cat_shocked), true));
        dataArrayList.add(new Data("", "", "", ContextCompat.getDrawable(this, R.drawable.cat_shocked), true));
    }
    private void prepareSomeDataForLoading() {
        dataArrayList.add(new Data("First City", "First State", "First Country", ContextCompat.getDrawable(this, R.drawable.cat_shocked), false));
        dataArrayList.add(new Data("Secnd City", "Secnd State", "Secnd Country", ContextCompat.getDrawable(this, R.drawable.cat_shocked), false));
        dataArrayList.add(new Data("Thrd City", "Thrd State", "Thrd Country", ContextCompat.getDrawable(this, R.drawable.cat_shocked), false));
        dataArrayList.add(new Data("Fourth City", "Fourth State", "Fourth Country", ContextCompat.getDrawable(this, R.drawable.cat_shocked), false));
        dataArrayList.add(new Data("Fifth City", "Fifth State", "Fifth Country", ContextCompat.getDrawable(this, R.drawable.cat_shocked), false));
        dataArrayList.add(new Data("Sixh City", "Sixh State", "Sixh Country", ContextCompat.getDrawable(this, R.drawable.cat_shocked), false));
        dataArrayList.add(new Data("Sevn City", "Sevn State", "Sevn Country", ContextCompat.getDrawable(this, R.drawable.cat_shocked), false));
    }

    private class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.adapter_row, viewGroup, false);
            LoadingTextView cityText = (LoadingTextView) view.findViewById(R.id.city);
            LoadingTextView stateText = (LoadingTextView) view.findViewById(R.id.state);
            LoadingTextView countryText = (LoadingTextView) view.findViewById(R.id.country);
            LoadingImageView cityImage = (LoadingImageView) view.findViewById(R.id.cityImage);
            cityText.setText(dataArrayList.get(i).getCityName());
            stateText.setText(dataArrayList.get(i).getStateName());
            countryText.setText(dataArrayList.get(i).getCountryName());
            cityImage.setImageDrawable(dataArrayList.get(i).getCityImage());
            if (dataArrayList.get(i).isLoading()) {
                cityText.startLoading();
                stateText.startLoading();
                countryText.startLoading();
                cityImage.startLoading();
            } else {
                cityText.stopLoading();
                stateText.stopLoading();
                countryText.stopLoading();
                cityImage.stopLoading();
            }
            return view;
        }
    }

}
