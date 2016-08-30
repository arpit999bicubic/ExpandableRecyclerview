package com.bicubic.expandablerecyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private RecipeAdapter mAdapter;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivity";

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_sample);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(new LightingColorFilter(0xFF000000, 0x81C784));

        Ingredient beef = new Ingredient("beef");
        Ingredient cheese = new Ingredient("cheese");
        Ingredient salsa = new Ingredient("salsa");
        Ingredient tortilla = new Ingredient("tortilla");
        Ingredient ketchup = new Ingredient("ketchup");
        Ingredient bun = new Ingredient("bun");

        Recipe taco = new Recipe("taco", Arrays.asList(beef, cheese, salsa, tortilla));
        Recipe quesadilla = new Recipe("quesadilla", Arrays.asList(cheese, tortilla));
        Recipe burger = new Recipe("burger", Arrays.asList(beef, cheese, ketchup, bun));
        final List<Recipe> recipes = Arrays.asList(taco, quesadilla, burger);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new RecipeAdapter(this, recipes);
        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                Recipe expandedRecipe = recipes.get(position);

                String toastMsg = getResources().getString(R.string.expanded, expandedRecipe.getName());
                Toast.makeText(MainActivity.this,
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onListItemCollapsed(int position) {
                Recipe collapsedRecipe = recipes.get(position);

                String toastMsg = getResources().getString(R.string.collapsed, collapsedRecipe.getName());
                Toast.makeText(MainActivity.this,
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new GetLiveData().execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAdapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.onRestoreInstanceState(savedInstanceState);
    }

    public class GetLiveData extends AsyncTask<String, Void, Void> {

        String responseString;
        Response response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... str) {

           /* OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("here is my url")
                    .build();

            try {

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                {
                    responseString = response.body().string();
                    System.out.println(responseString);
                    response.body().close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }*/

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Date now = new Date(); // java.util.Date, NOT java.sql.Date or java.sql.Timestamp!
            String current_date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(now);

//            Log.d("", "onPostExecute: date "+year+"-"+month+"-"+day);
            Log.d("", "onPostExecute: date " + current_date);

            responseString = "{\"timestamp\":1472455316256,\"list\":{\"Sport\":{\"2\":{\"id\":\"2\",\"name\":\"TENNIS\",\"code\":\"TEN\",\"hid\":\"5759708\",\"Matchday\":{\"2016-08-29\":{\"date\":\"2016-08-29\",\"Match\":{\"1368796\":{\"ct\":0,\"id\":\"1368796\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"44517\",\"name\":\"FRATANGELO B. (USA)\",\"serve\":\"0\",\"seed\":\"WC\",\"standing\":\"\"},\"Away\":{\"id\":\"41659\",\"name\":\"PELLA G. (ARG)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS YOUZHNY OR KLIZAN.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368797\":{\"ct\":0,\"id\":\"1368797\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"286\",\"name\":\"YOUZHNY M. (RUS)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"35579\",\"name\":\"KLIZAN M. (SVK)\",\"serve\":\"0\",\"seed\":\"28\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS FRATANGELO OR PELLA.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368799\":{\"ct\":0,\"id\":\"1368799\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"43093\",\"name\":\"THOMPSON J. (AUS)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"28204\",\"name\":\"DARCIS S. (BEL)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS ISNER OR TIAFOE.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368800\":{\"ct\":0,\"id\":\"1368800\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"26967\",\"name\":\"LACKO L. (SVK)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"47864\",\"name\":\"ESCOBEDO E. (USA)\",\"serve\":\"0\",\"seed\":\"WC\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS EDMUND OR GASQUET.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368801\":{\"ct\":0,\"id\":\"1368801\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"41813\",\"name\":\"EDMUND K. (GBR)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"6647\",\"name\":\"GASQUET R. (FRA)\",\"serve\":\"0\",\"seed\":\"13\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS LACKO OR ESCOBEDO.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368808\":{\"ct\":0,\"id\":\"1368808\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"21961\",\"name\":\"STAKHOVSKY S. (UKR)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"27575\",\"name\":\"ELIAS G. (POR)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS DUTRA SILVA OR CILIC.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368809\":{\"ct\":0,\"id\":\"1368809\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"43809\",\"name\":\"DUTRA SILVA R. (BRA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"24289\",\"name\":\"CILIC M. (CRO)\",\"serve\":\"0\",\"seed\":\"7\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS STAKHOVSKY OR ELIAS.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368812\":{\"ct\":0,\"id\":\"1368812\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"31194\",\"name\":\"BELLUCCI T. (BRA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"36224\",\"name\":\"KUZNETSOV AN. (RUS)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS BENNETEAU OR RAMOS-VINOLAS.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368813\":{\"ct\":0,\"id\":\"1368813\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"908\",\"name\":\"BENNETEAU J. (FRA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"37090\",\"name\":\"RAMOS-VINOLAS A. (ESP)\",\"serve\":\"0\",\"seed\":\"31\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS BELLUCCI OR KUZNETSOV AN.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368897\":{\"ct\":0,\"id\":\"1368897\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"43480\",\"name\":\"TOWNSEND T. (USA)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Away\":{\"id\":\"24068\",\"name\":\"WOZNIACKI C. (DEN)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS SCHIAVONE OR KUZNETSOVA.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368901\":{\"ct\":0,\"id\":\"1368901\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"23126\",\"name\":\"PETKOVIC A. (GER)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"35250\",\"name\":\"KUCOVA K. (SVK)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS CRAWFORD OR BENCIC.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368902\":{\"ct\":0,\"id\":\"1368902\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"42375\",\"name\":\"CRAWFORD S. (USA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"42821\",\"name\":\"BENCIC B. (SUI)\",\"serve\":\"0\",\"seed\":\"24\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS PETKOVIC OR KUCOVA K.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368907\":{\"ct\":0,\"id\":\"1368907\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"5508\",\"name\":\"VINCI R. (ITA)\",\"serve\":\"0\",\"seed\":\"7\",\"standing\":\"\"},\"Away\":{\"id\":\"42754\",\"name\":\"FRIEDSAM A-L. (GER)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS MCHALE OR BARTHEL.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368910\":{\"ct\":0,\"id\":\"1368910\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"42114\",\"name\":\"WITTHOEFT C. (GER)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"37334\",\"name\":\"DOI M. (JPN)\",\"serve\":\"0\",\"seed\":\"30\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS PUTINTSEVA OR LISICKI.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368915\":{\"ct\":0,\"id\":\"1368915\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"30671\",\"name\":\"KVITOVA P. (CZE)\",\"serve\":\"0\",\"seed\":\"14\",\"standing\":\"\"},\"Away\":{\"id\":\"48042\",\"name\":\"OSTAPENKO J. (LAT)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS FALCONI OR BUYUKAKCAY.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368916\":{\"ct\":0,\"id\":\"1368916\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"15:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"38086\",\"name\":\"FALCONI I. (USA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"23210\",\"name\":\"BUYUKAKCAY C. (TUR)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS KVITOVA OR OSTAPENKO.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368798\":{\"ct\":0,\"id\":\"1368798\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"30486\",\"name\":\"ISNER J. (USA)\",\"serve\":\"0\",\"seed\":\"20\",\"standing\":\"\"},\"Away\":{\"id\":\"47648\",\"name\":\"TIAFOE F. (USA)\",\"serve\":\"0\",\"seed\":\"WC\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS THOMPSON OR DARCIS.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368804\":{\"ct\":0,\"id\":\"1368804\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"40186\",\"name\":\"POSPISIL V. (CAN)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"43814\",\"name\":\"KOVALIK J. (SVK)\",\"serve\":\"0\",\"seed\":\"LL\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS NISHIOKA OR ANDERSON K.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368811\":{\"ct\":0,\"id\":\"1368811\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"16910\",\"name\":\"ROBERT S. (FRA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"5647\",\"name\":\"SEPPI A. (ITA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS NADAL OR ISTOMIN.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368818\":{\"ct\":0,\"id\":\"1368818\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"21929\",\"name\":\"MONFILS G. (FRA)\",\"serve\":\"0\",\"seed\":\"10\",\"standing\":\"\"},\"Away\":{\"id\":\"5784\",\"name\":\"MULLER G. (LUX)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS SATRAL OR MCDONALD.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368820\":{\"ct\":0,\"id\":\"1368820\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"41807\",\"name\":\"FUCSOVICS M. (HUN)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Away\":{\"id\":\"5750\",\"name\":\"ALMAGRO N. (ESP)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS SELA OR CUEVAS.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368893\":{\"ct\":0,\"id\":\"1368893\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"51469\",\"name\":\"SAKKARI M. (GRE)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"42379\",\"name\":\"DUAN Y-Y. (CHN)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS OSAKA OR VANDEWEGHE.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368894\":{\"ct\":0,\"id\":\"1368894\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"47673\",\"name\":\"OSAKA N. (JPN)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"28298\",\"name\":\"VANDEWEGHE C. (USA)\",\"serve\":\"0\",\"seed\":\"28\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS SAKKARI OR DUAN Y-Y.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368898\":{\"ct\":0,\"id\":\"1368898\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"1101\",\"name\":\"SCHIAVONE F. (ITA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"8518\",\"name\":\"KUZNETSOVA S. (RUS)\",\"serve\":\"0\",\"seed\":\"9\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS TOWNSEND OR WOZNIACKI.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368905\":{\"ct\":0,\"id\":\"1368905\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"29949\",\"name\":\"SEVASTOVA A. (LAT)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"43116\",\"name\":\"SCHMIEDLOVA A. K. (SVK)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS MERTENS OR MUGURUZA.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368908\":{\"ct\":0,\"id\":\"1368908\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"34092\",\"name\":\"MCHALE C. (USA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"38456\",\"name\":\"BARTHEL M. (GER)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS VINCI OR FRIEDSAM.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368909\":{\"ct\":0,\"id\":\"1368909\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"38425\",\"name\":\"PUTINTSEVA Y. (KAZ)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"23128\",\"name\":\"LISICKI S. (GER)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS WITTHOEFT OR DOI.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368912\":{\"ct\":0,\"id\":\"1368912\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"44437\",\"name\":\"WANG YAF. (CHN)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Away\":{\"id\":\"41443\",\"name\":\"VAN UYTVANCK A.(BEL)\",\"serve\":\"0\",\"seed\":\"LL\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS BEGU OR TSURENKO.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368919\":{\"ct\":0,\"id\":\"1368919\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"27044\",\"name\":\"ERRANI S. (ITA)\",\"serve\":\"0\",\"seed\":\"27\",\"standing\":\"\"},\"Away\":{\"id\":\"38078\",\"name\":\"ROGERS S. (USA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS BELLIS OR GOLUBIC.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368920\":{\"ct\":0,\"id\":\"1368920\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"47900\",\"name\":\"BELLIS C. (USA)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Away\":{\"id\":\"44291\",\"name\":\"GOLUBIC V. (SUI)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS ERRANI OR ROGERS.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368921\":{\"ct\":0,\"id\":\"1368921\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"26643\",\"name\":\"CORNET A. (FRA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"5842\",\"name\":\"LUCIC-BARONI M. (CRO)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS HERCOG OR KERBER.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368922\":{\"ct\":0,\"id\":\"1368922\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"17:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"31080\",\"name\":\"HERCOG P. (SLO)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"23131\",\"name\":\"KERBER A. (GER)\",\"serve\":\"0\",\"seed\":\"2\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS CORNET OR LUCIC-BARONI.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368802\":{\"ct\":0,\"id\":\"1368802\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"21797\",\"name\":\"TSONGA J-W. (FRA)\",\"serve\":\"0\",\"seed\":\"9\",\"standing\":\"\"},\"Away\":{\"id\":\"42362\",\"name\":\"ANDREOZZI G. (ARG)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS DUCKWORTH OR HAASE.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368805\":{\"ct\":0,\"id\":\"1368805\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"47865\",\"name\":\"NISHIOKA Y. (JPN)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"30853\",\"name\":\"ANDERSON K. (RSA)\",\"serve\":\"0\",\"seed\":\"23\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS POSPISIL V. OR KOVALIK.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368807\":{\"ct\":0,\"id\":\"1368807\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"27883\",\"name\":\"ZVEREV M. (GER)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Away\":{\"id\":\"41651\",\"name\":\"HERBERT P-H. (FRA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS SOCK OR FRITZ.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368810\":{\"ct\":0,\"id\":\"1368810\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"6658\",\"name\":\"NADAL R. (ESP)\",\"serve\":\"0\",\"seed\":\"4\",\"standing\":\"\"},\"Away\":{\"id\":\"26636\",\"name\":\"ISTOMIN D. (UZB)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS ROBERT OR SEPPI.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368815\":{\"ct\":0,\"id\":\"1368815\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"39100\",\"name\":\"CLEZAR G. (BRA)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Away\":{\"id\":\"2351\",\"name\":\"CHIUDINELLI M. (SUI)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS POUILLE OR KUKUSHKIN.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368816\":{\"ct\":0,\"id\":\"1368816\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"6758\",\"name\":\"BAKER B. (USA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"35572\",\"name\":\"DELBONIS F. (ARG)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS GARCIA-LOPEZ OR BAUTISTA AGUT.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368819\":{\"ct\":0,\"id\":\"1368819\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"51477\",\"name\":\"SATRAL J. (CZE)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Away\":{\"id\":\"44467\",\"name\":\"MCDONALD M. (USA)\",\"serve\":\"0\",\"seed\":\"WC\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS MONFILS OR MULLER.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368821\":{\"ct\":0,\"id\":\"1368821\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"23264\",\"name\":\"SELA D. (ISR)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"30878\",\"name\":\"CUEVAS P. (URU)\",\"serve\":\"0\",\"seed\":\"18\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS FUCSOVICS OR ALMAGRO.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368823\":{\"ct\":0,\"id\":\"1368823\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"37333\",\"name\":\"PAIRE B. (FRA)\",\"serve\":\"0\",\"seed\":\"32\",\"standing\":\"\"},\"Away\":{\"id\":\"37175\",\"name\":\"LAJOVIC D. (SRB)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS BAGHDATIS OR BAGNIS.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368824\":{\"ct\":0,\"id\":\"1368824\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"21084\",\"name\":\"BAGHDATIS M. (CYP)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"39039\",\"name\":\"BAGNIS F. (ARG)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS PAIRE OR LAJOVIC.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368900\":{\"ct\":0,\"id\":\"1368900\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"26594\",\"name\":\"PIRONKOVA T. (BUL)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"1661\",\"name\":\"RAZZANO V. (FRA)\",\"serve\":\"0\",\"seed\":\"WC\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS KONTA OR MATTEK-SANDS.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368903\":{\"ct\":0,\"id\":\"1368903\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"39415\",\"name\":\"PUIG M. (PUR)\",\"serve\":\"0\",\"seed\":\"32\",\"standing\":\"\"},\"Away\":{\"id\":\"40506\",\"name\":\"ZHENG S. (CHN)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS HSIEH S-W. OR BONDARENKO K.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368906\":{\"ct\":0,\"id\":\"1368906\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"50723\",\"name\":\"MERTENS E. (BEL)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Away\":{\"id\":\"41366\",\"name\":\"MUGURUZA G. (ESP)\",\"serve\":\"0\",\"seed\":\"3\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS SEVASTOVA OR SCHMIEDLOVA A. K.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368911\":{\"ct\":0,\"id\":\"1368911\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"35382\",\"name\":\"BEGU I-C. (ROU)\",\"serve\":\"0\",\"seed\":\"21\",\"standing\":\"\"},\"Away\":{\"id\":\"36082\",\"name\":\"TSURENKO L. (UKR)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS WANG YAF. OR VAN UYTVANCK.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368913\":{\"ct\":0,\"id\":\"1368913\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"53919\",\"name\":\"COLLINS D. (USA)\",\"serve\":\"0\",\"seed\":\"WC\",\"standing\":\"\"},\"Away\":{\"id\":\"25462\",\"name\":\"RODINA E. (RUS)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS LINETTE OR CIBULKOVA.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368914\":{\"ct\":0,\"id\":\"1368914\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"19:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"43811\",\"name\":\"LINETTE M. (POL)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"27677\",\"name\":\"CIBULKOVA D. (SVK)\",\"serve\":\"0\",\"seed\":\"12\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS COLLINS OR RODINA.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368795\":{\"ct\":0,\"id\":\"1368795\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"43313\",\"name\":\"VESELY J. (CZE)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"50693\",\"name\":\"MYNENI S. (IND)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS DJOKOVIC OR JANOWICZ.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368803\":{\"ct\":0,\"id\":\"1368803\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"40901\",\"name\":\"DUCKWORTH J. (AUS)\",\"serve\":\"0\",\"seed\":\"WC\",\"standing\":\"\"},\"Away\":{\"id\":\"27852\",\"name\":\"HAASE R. (NED)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS TSONGA OR ANDREOZZI.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368814\":{\"ct\":0,\"id\":\"1368814\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"43090\",\"name\":\"POUILLE L. (FRA)\",\"serve\":\"0\",\"seed\":\"24\",\"standing\":\"\"},\"Away\":{\"id\":\"31424\",\"name\":\"KUKUSHKIN M. (KAZ)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS CLEZAR OR CHIUDINELLI.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368817\":{\"ct\":0,\"id\":\"1368817\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"32459\",\"name\":\"GARCIA-LOPEZ G. (ESP)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"32505\",\"name\":\"BAUTISTA AGUT R. (ESP)\",\"serve\":\"0\",\"seed\":\"15\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS BAKER OR DELBONIS.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368825\":{\"ct\":0,\"id\":\"1368825\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"32375\",\"name\":\"MANNARINO A. (FRA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"32156\",\"name\":\"HARRISON R. (USA)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS BROWN D. OR RAONIC.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368826\":{\"ct\":0,\"id\":\"1368826\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"38780\",\"name\":\"BROWN D. (GER)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"35759\",\"name\":\"RAONIC M. (CAN)\",\"serve\":\"0\",\"seed\":\"5\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS MANNARINO OR HARRISON R.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368892\":{\"ct\":0,\"id\":\"1368892\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"29248\",\"name\":\"BRENGLE M. (USA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"53883\",\"name\":\"DAY K. (USA)\",\"serve\":\"0\",\"seed\":\"WC\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS KEYS OR RISKE.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368895\":{\"ct\":0,\"id\":\"1368895\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"29198\",\"name\":\"STRYCOVA B. (CZE)\",\"serve\":\"0\",\"seed\":\"18\",\"standing\":\"\"},\"Away\":{\"id\":\"29470\",\"name\":\"NICULESCU M. (ROU)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS CIRSTEA OR BOGDAN.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368896\":{\"ct\":0,\"id\":\"1368896\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"29761\",\"name\":\"CIRSTEA S. (ROU)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"47434\",\"name\":\"BOGDAN A. (ROU)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS STRYCOVA OR NICULESCU.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368899\":{\"ct\":0,\"id\":\"1368899\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"39730\",\"name\":\"KONTA J. (GBR)\",\"serve\":\"0\",\"seed\":\"13\",\"standing\":\"\"},\"Away\":{\"id\":\"4784\",\"name\":\"MATTEK-SANDS B. (USA)\",\"serve\":\"0\",\"seed\":\"WC\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS PIRONKOVA OR RAZZANO.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368904\":{\"ct\":0,\"id\":\"1368904\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"6849\",\"name\":\"HSIEH S-W. (TPE)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"4715\",\"name\":\"BONDARENKO K. (UKR)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS PUIG OR ZHENG S.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368917\":{\"ct\":0,\"id\":\"1368917\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"41448\",\"name\":\"SASNOVICH A. (BLR)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Away\":{\"id\":\"38946\",\"name\":\"DAVIS L. (USA)\",\"serve\":\"0\",\"seed\":\"WC\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS MINELLA OR SVITOLINA.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368918\":{\"ct\":0,\"id\":\"1368918\",\"lastPeriod\":\"\",\"leagueCode\":\"36584\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"22644\",\"name\":\"MINELLA M. (LUX)\",\"serve\":\"0\",\"seed\":\"Q\",\"standing\":\"\"},\"Away\":{\"id\":\"41446\",\"name\":\"SVITOLINA E. (UKR)\",\"serve\":\"0\",\"seed\":\"22\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"50121\",\"name\":\"WTA SINGLES\"},\"league\":{\"id\":\"36584\",\"name\":\"US OPEN\"},\"shortName\":\"WTA-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS SASNOVICH OR DAVIS.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368806\":{\"ct\":0,\"id\":\"1368806\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"21:30\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"38073\",\"name\":\"SOCK J. (USA)\",\"serve\":\"0\",\"seed\":\"26\",\"standing\":\"\"},\"Away\":{\"id\":\"47866\",\"name\":\"FRITZ T. (USA)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS ZVEREV M. OR HERBERT.\",\"bitArray\":\"\",\"timestamp\":\"\"}},\"1368794\":{\"ct\":0,\"id\":\"1368794\",\"lastPeriod\":\"\",\"leagueCode\":\"36424\",\"leagueSort\":0,\"leagueType\":\"Tournament\",\"startTime\":\"23:00\",\"status\":\"Sched\",\"statustype\":\"sched\",\"type\":2,\"visible\":\"1\",\"Home\":{\"id\":\"21374\",\"name\":\"DJOKOVIC N. (SRB)\",\"serve\":\"0\",\"seed\":\"1\",\"standing\":\"\"},\"Away\":{\"id\":\"28141\",\"name\":\"JANOWICZ J. (POL)\",\"serve\":\"0\",\"seed\":\"\",\"standing\":\"\"},\"Information\":{\"season\":{\"id\":\"333\",\"name\":\"2016\"},\"country\":{\"id\":\"49948\",\"name\":\"ATP SINGLES\"},\"league\":{\"id\":\"36424\",\"name\":\"US OPEN\"},\"shortName\":\"ATP-S\",\"round\":\"R128\",\"note\":\"WINNER PLAYS VESELY OR MYNENI.\",\"bitArray\":\"\",\"timestamp\":\"\"}}}}}}}}}\n";

            if (responseString != null) {

                try {
                    String tournament = null, home_player = null, home_player_point = null, away_player = null, away_player_point = null,HomeOne=null,AwayOne=null,HomeTwo=null,AwayTwo=null,HomeThree=null,AwayThree=null,HomeFour=null,AwayFour=null,HomeFive=null,AwayFive=null;

                    JSONObject MainObject = new JSONObject(responseString).getJSONObject("list").getJSONObject("Sport").getJSONObject("2").getJSONObject("Matchday").getJSONObject("2016-08-20").getJSONObject("Match");

//                    Log.d("", "onPostExecute: jObject = " + MainObject);

                    Iterator<String> keys = MainObject.keys();
                    while (keys.hasNext()) {
                        String MatchNoKey = keys.next();
                        Log.v("list MatchNoKey = ", MatchNoKey);

                        JSONObject MatchNoObj;

                        if(MainObject.get(MatchNoKey) instanceof JSONObject) {

                            MatchNoObj = MainObject.getJSONObject(MatchNoKey);
                            String id = MatchNoObj.getString("id");
                            String leagueType = MatchNoObj.getString("leagueType");
                            String startTime = MatchNoObj.getString("startTime");
                            String status = MatchNoObj.getString("status");
                            String leagueCode = MatchNoObj.getString("leagueCode");
                            Log.v("details", "id = " + id + ", " + "leagueType = " + leagueType + ", " + "startTime = " + startTime + ", " + "status = " + status + ", " + "leagueCode = " + leagueCode );
//                            after complete matchno object info
//                            now we get the home Object
                            if(MatchNoObj.has("Home")) {

                                JSONObject HomeObj = MatchNoObj.getJSONObject("Home");

                                Log.d(TAG, "onPostExecute: Home pass");

                                String id_home = HomeObj.getString("id");
                                home_player = HomeObj.getString("name");
                                String serve = HomeObj.getString("serve");

                                Log.v("details", "id_home = " + id_home + ", " + "player0ne = " + home_player + ", " + "serve = " + serve );

                            }

//                            Away Object
                            if(MatchNoObj.has("Away")){

                                JSONObject AwayObj = MatchNoObj.getJSONObject("Away");

                                Log.d(TAG, "onPostExecute: Away pass");

                                String id_away = AwayObj.getString("id");
                                away_player = AwayObj.getString("name");
                                String serve = AwayObj.getString("serve");

                                Log.v("details", "id_away = " + id_away + ", " + "playerTwo = " + away_player + ", " + "serve = " + serve );

                            }


//                            Result object
                            if(MatchNoObj.has("Results")){

                                JSONObject ResultObj = MatchNoObj.getJSONObject("Results");

                                Log.d(TAG, "onPostExecute: ResultObj pass");

                                if (ResultObj.has("1")){

                                    JSONObject leagueObj = ResultObj.getJSONObject("league");
                                    String CurrentScore = leagueObj.getString("value");
                                    String[] score = CurrentScore.split("-");

                                    home_player_point = score[0];
                                    away_player_point = score[1];
                                }

                                if (ResultObj.has("Period")){

                                    JSONObject PeriodObj = ResultObj.getJSONObject("league");
                                    Iterator<String> Periodkeys = PeriodObj.keys();

                                    while (Periodkeys.hasNext()) {
                                        String innerkey = Periodkeys.next();
                                        Log.v("list key", innerkey);

                                        if(PeriodObj.get(innerkey) instanceof JSONObject) {

                                            JSONObject innerJObject = PeriodObj.getJSONObject(innerkey);
                                            String current_score = innerJObject.getString("value");
                                            String tiebreak = innerJObject.getString("tiebreak");

                                            Log.v("details ", "current_score = " + current_score + ", " + "tiebreak = " + tiebreak );

                                        } else if (PeriodObj.get(innerkey) instanceof String){
                                            String value = PeriodObj.getString("type");
                                            Log.v("key = type", "value = " + value);
                                        }

                                    }
                                }

                                Log.v("details information", "home_player_point = "+home_player_point+ ", " + "away_player_point = " + away_player_point);

                            }

//                            Information Object
                            if(MatchNoObj.has("Information")){

                                JSONObject InfoObj = MatchNoObj.getJSONObject("Information");

                                Log.d(TAG, "onPostExecute: InfoObj pass");

                                String shortName = InfoObj.getString("shortName");
                                String round = InfoObj.getString("round");
                                String note = InfoObj.getString("note");
                                String leagueName=null;

                                if (InfoObj.has("league")){

                                    JSONObject leagueObj = InfoObj.getJSONObject("league");
                                    tournament = leagueObj.getString("name")+" "+shortName;
                                }

                                Log.v("details information", "tournament = "+tournament+ ", " + "round = " + round + ", " + "note = " + note );

                            }


                        } else if (MainObject.get(MatchNoKey) instanceof String){
                            MatchNoObj = MainObject.getJSONObject(MatchNoKey);
                            String value = MatchNoObj.getString(MatchNoKey);
                            Log.v("key = type", "value = " + value);
                        }


                    }

                    progressBar.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Couldn't get Data from server", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
