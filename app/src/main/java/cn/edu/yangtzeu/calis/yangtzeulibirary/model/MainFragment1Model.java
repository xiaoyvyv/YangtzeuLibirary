package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.SearchBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IMainFragment1Model;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BaseActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BookListActivity;

public class MainFragment1Model implements IMainFragment1Model {
    private final SearchBean SearchBean = new SearchBean();

    @Override
    public void searchData(final Activity activity, final SearchView searchView) {
        //searchView.onActionViewExpanded();//关闭的搜索图标
        searchView.setIconifiedByDefault(true);//默认为true在框内，设置false则在框外
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearch(activity,query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void fitHotWord(final Context context, final SearchView searchView, final LinearLayout layout1, final LinearLayout layout2, final LinearLayout layout3) {
        OkHttp.do_Get(Urls.Url_Index, new OnResultListener() {
            @Override
            @SuppressLint("InflateParams")
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                Elements hot = document.select("div.hot");
                Elements hot_a = hot.get(0).select("div a");
                for (int i = 0; i < 3; i++) {
                    TextView textView1 = (TextView) LayoutInflater.from(context).inflate(R.layout.fragment1_item, null);
                    textView1.setOnClickListener(new ClickListener((Activity) context, searchView));
                    TextView textView2 = (TextView) LayoutInflater.from(context).inflate(R.layout.fragment1_item, null);
                    textView2.setOnClickListener(new ClickListener((Activity) context, searchView));
                    TextView textView3 = (TextView) LayoutInflater.from(context).inflate(R.layout.fragment1_item, null);
                    textView3.setOnClickListener(new ClickListener((Activity) context, searchView));
                    String word1 = hot_a.get(i).text().trim();
                    String word2 = hot_a.get(i + 3).text().trim();
                    String word3 = hot_a.get(i + 2 * 3).text().trim();
                    textView1.setText(word1);
                    layout1.addView(textView1);
                    textView2.setText(word2);
                    layout2.addView(textView2);
                    textView3.setText(word3);
                    layout3.addView(textView3);

                }
            }
            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                fitHotWord(context,searchView,layout1, layout2, layout3);
            }
        });
    }

    @SuppressLint({"InflateParams","UseSparseArrays"})
    @Override
    public void showHighSearch(Activity activity) {
        final String[] data = activity.getResources().getStringArray(R.array.data);
        final String[] data1 = activity.getResources().getStringArray(R.array.data1);
        final String[] sort_choose_value = activity.getResources().getStringArray(R.array.sort_choose_value);
        final String[] sort_kind_value = activity.getResources().getStringArray(R.array.sort_kind_value);
        final HashMap<Integer, String> hashMap = new HashMap<>();

        for (int i = 0; i < 6; i++) {
            hashMap.put(i, "");
        }

        View view =  LayoutInflater.from(activity).inflate(R.layout.fragment1_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setView(view)
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        StringBuilder lib = new StringBuilder();
                        for (int i = 0; i < 6; i++) {
                            String s = hashMap.get(i);
                            if (!s.isEmpty()) {
                                lib.append("&curlibcode=").append(s);
                            }
                        }
                        SearchBean.setCurlibcode(lib.toString());
                    }
                })
                .create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        RadioGroup radioGroup = view.findViewById(R.id.mRadioGroup);
        RadioGroup radioGroup1 = view.findViewById(R.id.mRadioGroup1);

        CheckBox lib_cd = view.findViewById(R.id.lib_cd);
        lib_cd.setOnCheckedChangeListener(new OnCheckChangeListener(hashMap, 0));
        CheckBox lib_gc = view.findViewById(R.id.lib_gc);
        lib_gc.setOnCheckedChangeListener(new OnCheckChangeListener(hashMap, 1));
        CheckBox lib_lc = view.findViewById(R.id.lib_lc);
        lib_lc.setOnCheckedChangeListener(new OnCheckChangeListener(hashMap, 2));
        CheckBox lib_wl = view.findViewById(R.id.lib_wl);
        lib_wl.setOnCheckedChangeListener(new OnCheckChangeListener(hashMap, 3));
        CheckBox lib_xn = view.findViewById(R.id.lib_xn);
        lib_xn.setOnCheckedChangeListener(new OnCheckChangeListener(hashMap, 4));
        CheckBox lib_yx = view.findViewById(R.id.lib_yx);
        lib_yx.setOnCheckedChangeListener(new OnCheckChangeListener(hashMap, 5));


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.all:
                        SearchBean.setBooktype("");
                        break;
                    case R.id.image_book:
                        SearchBean.setBooktype("1");
                        break;
                    case R.id.news:
                        SearchBean.setBooktype("2");
                        break;
                    case R.id.not_book:
                        SearchBean.setBooktype("3");
                        break;
                    case R.id.old_book:
                        SearchBean.setBooktype("4");
                        break;
                    case R.id.toy:
                        SearchBean.setBooktype("5");
                        break;
                }

            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.all1:
                        SearchBean.setMarcformat("");
                        break;
                    case R.id.chinese:
                        SearchBean.setMarcformat("CNMARC");
                        break;
                    case R.id.english:
                        SearchBean.setMarcformat("USMARC");
                }
            }
        });

        Spinner start_data= view.findViewById(R.id.start_data);
        Spinner end_data= view.findViewById(R.id.end_data);
        Spinner choose_sort= view.findViewById(R.id.sort_choose);
        Spinner kind_sort= view.findViewById(R.id.sort_kind);

        start_data.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = data1[position];
                SearchBean.setStartPubdate(string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        end_data.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = data[position];
                SearchBean.setEndPubdate(string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        choose_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = sort_choose_value[position];
                SearchBean.setSortWay(string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        kind_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = sort_kind_value[position];
                SearchBean.setSortOrder(string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }

    @Override
    public void setSearchWay(RadioGroup radioGroup) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.RenYi:
                       SearchBean.setSearchWay("");
                        break;
                    case R.id.TiMing:
                        SearchBean.setSearchWay("title");
                        break;
                    case R.id.ZuoZhe:
                        SearchBean.setSearchWay("author");
                        break;
                    case R.id.ZhuTi:
                        SearchBean.setSearchWay("subject");
                        break;
                }
            }
        });
    }

    @Override
    public void setOnlySavedLib(CheckBox checkBox) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SearchBean.setHasholding("1");
                } else {
                    SearchBean.setHasholding("");
                }
            }
        });
    }

    private void startSearch(Activity activity,String q) {
        KeyboardUtils.hideSoftInput(activity);
        SearchBean.setQ(q);

        String url = Urls.Url_Search +SearchBean.getAll();

        Intent intent = new Intent(activity, BookListActivity.class);
        intent.putExtra("from_url", url);
        intent.putExtra("title", q);
        activity.startActivity(intent);
    }

    private class ClickListener implements View.OnClickListener {
        private SearchView searchView;
        private Activity activity;

        ClickListener(Activity activity, SearchView searchView) {
            this.searchView = searchView;
            this.activity = activity;
        }

        @Override
        public void onClick(View v) {
            searchView.setIconified(false);//设置searchView处于展开状态
            SearchView.SearchAutoComplete mSearchAutoComplete = searchView.findViewById(R.id.search_src_text);
            mSearchAutoComplete.setText(((TextView)v).getText().toString());//设置输入框提示文字样式
            startSearch(activity,((TextView) v).getText().toString());
        }
    }

    private class OnCheckChangeListener implements CompoundButton.OnCheckedChangeListener{
        private int position;
        private HashMap<Integer,String> searchBean;

        OnCheckChangeListener(HashMap<Integer,String> searchBean, int position) {
            this.position = position;
            this.searchBean = searchBean;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                switch (position) {
                    case 0:
                        searchBean.put(0, "CD");
                        break;
                    case 1:
                        searchBean.put(1, "JZTC");
                        break;
                    case 2:
                        searchBean.put(2, "LC");
                        break;
                    case 3:
                        searchBean.put(3, "WLXY");
                        break;
                    case 4:
                        searchBean.put(4, "999");
                        break;
                    case 5:
                        searchBean.put(5, "YX");
                        break;
                }
            } else {
                searchBean.put(position, "");
            }
        }
    }

}
