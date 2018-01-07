package bhouse.radiovolumes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import bhouse.radiovolumes.helpLibraries.LocaleHelper;
import bhouse.radiovolumes.helpLibraries.MainPageItem;
import bhouse.radiovolumes.helpLibraries.MainPageItemsData;
import bhouse.radiovolumes.helpLibraries.TransitionAdapter;
import bhouse.radiovolumes.processor.OARTemplate;
import bhouse.radiovolumes.processor.OARListXMLHandler;
import bhouse.radiovolumes.processor.XYPair;
import bhouse.radiovolumes.processor.XYXMLHandler;

/**
 * Created by megha on 15-03-10.
 */
public class OARSelectionActivity extends Activity implements View.OnClickListener {

  public static final String EXTRA_PARAM_ID = "place_id";
  public static final String NAV_BAR_VIEW_NAME = Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME;
  private ListView mList;
  private ImageView mImageView;
  private TextView mTitle;
  private LinearLayout mTitleHolder;
  private Palette mPalette;
  private ImageButton mAddButton;
  private Animatable mAnimatable;
  private LinearLayout mRevealView;
  private EditText mEditTextTodo;
  private boolean isEditTextVisible;
  private InputMethodManager mInputManager;
  private MainPageItem mMainPageItem;
  private ArrayList<String> mTodoList;
  private OARSelectionAdapter mToDoAdapter;
  private ArrayList<OARTemplate> OARTemplateList;
  int defaultColorForRipple;
  private ArrayList<Item> allIncludedList = new ArrayList<Item>();
  private HashMap<String, HashMap<String, XYPair<String,String>>> xyValues;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    MainPageItemsData mainPageItemsData = new MainPageItemsData(getApplicationContext());

    mMainPageItem = mainPageItemsData.placeList().get(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));

    mList = (ListView) findViewById(R.id.list);
    mImageView = (ImageView) findViewById(R.id.placeImage);
    mTitle = (TextView) findViewById(R.id.textView);
    mTitleHolder = (LinearLayout) findViewById(R.id.placeNameHolder);
    mAddButton = (ImageButton) findViewById(R.id.btn_add);
    mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);
    mEditTextTodo = (EditText) findViewById(R.id.etTodo);

    mAddButton.setImageResource(R.drawable.icn_morph_reverse);
    mAddButton.setOnClickListener(this);
    defaultColorForRipple = getResources().getColor(R.color.primary_dark);
    mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    mRevealView.setVisibility(View.INVISIBLE);
    isEditTextVisible = false;

    mTodoList = new ArrayList<>();
    //mToDoAdapter = new ArrayAdapter(this, R.layout.row_todo, mTodoList);


    OARTemplateList = new ArrayList<OARTemplate>();
    Locale current = getResources().getConfiguration().locale;
    try {
      if (current.toLanguageTag().equals("fr")){
        OARListXMLHandler parser = new OARListXMLHandler();
        OARTemplateList = parser.parse(getAssets().open("OARlimits_FR.xml"));
      }
      else {
        OARListXMLHandler parser = new OARListXMLHandler();
        OARTemplateList = parser.parse(getAssets().open("OARlimits_EN.xml"));
      }


    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      XYXMLHandler parser = new XYXMLHandler();
      this.xyValues = parser.parse(getAssets().open("xyvalues.xml"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    prepareOARData();
    mToDoAdapter = new OARSelectionAdapter(getApplicationContext(),this.allIncludedList, this.OARTemplateList);
    mList.setAdapter(mToDoAdapter);

    ViewGroup tHeader = (ViewGroup)getLayoutInflater().inflate(R.layout.header_oar, mList, false);
    mList.addHeaderView(tHeader, null, false);


    loadPlace();
    windowTransition();
    getPhoto();

  }

  private void loadPlace() {
    mTitle.setText(mMainPageItem.name);
    mImageView.setImageResource(mMainPageItem.getImageResourceId(this));
  }

  private void windowTransition() {
    getWindow().setEnterTransition(makeEnterTransition());
    getWindow().getEnterTransition().addListener(new TransitionAdapter() {
      @Override
      public void onTransitionEnd(Transition transition) {
        mAddButton.animate().alpha(1.0f);
        getWindow().getEnterTransition().removeListener(this);
      }
    });
  }

  public static Transition makeEnterTransition() {
    Transition fade = new Fade();
    fade.excludeTarget(android.R.id.navigationBarBackground, true);
    fade.excludeTarget(android.R.id.statusBarBackground, true);
    return fade;
  }

  private void addToDo(String todo) {
    mTodoList.add(todo);
  }

  private void getPhoto() {
    Bitmap photo = BitmapFactory.decodeResource(getResources(), mMainPageItem.getImageResourceId(this));
    colorize(photo);
  }

  private void colorize(Bitmap photo) {
    mPalette = Palette.generate(photo);
    applyPalette();
  }

  private void applyPalette() {
    getWindow().setBackgroundDrawable(new ColorDrawable(mPalette.getDarkMutedColor(defaultColorForRipple)));
    mTitleHolder.setBackgroundColor(mPalette.getMutedColor(defaultColorForRipple));
    applyRippleColor(mPalette.getVibrantColor(defaultColorForRipple),
            mPalette.getDarkVibrantColor(defaultColorForRipple));
    mRevealView.setBackgroundColor(mPalette.getLightVibrantColor(defaultColorForRipple));
  }

  private void applyRippleColor(int bgColor, int tintColor) {
    colorRipple(mAddButton, bgColor, tintColor);
  }

  private void colorRipple(ImageButton id, int bgColor, int tintColor) {
    View buttonView = id;
    RippleDrawable ripple = (RippleDrawable) buttonView.getBackground();
    GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
    rippleBackground.setColor(bgColor);
    ripple.setColor(ColorStateList.valueOf(tintColor));
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_add:
        if (!isEditTextVisible) {
          //revealEditText(mRevealView);
          //mEditTextTodo.requestFocus();
          //mInputManager.showSoftInput(mEditTextTodo, InputMethodManager.SHOW_IMPLICIT);
          mAddButton.setImageResource(R.drawable.icn_morp);
          mAnimatable = (Animatable) (mAddButton).getDrawable();
          mAnimatable.start();
          applyRippleColor(getResources().getColor(R.color.light_green), getResources().getColor(R.color.dark_green));
          Intent transitionIntent = new Intent(OARSelectionActivity.this, ScannerOARViewActivity_simple.class);
          transitionIntent.putExtra("OAR", this.OARTemplateList);
          transitionIntent.putExtra("XY", this.xyValues);
          if(!((Activity) v.getContext()).isFinishing())
          {
            startActivity(transitionIntent);//show dialog
          }

        } else {
          addToDo(mEditTextTodo.getText().toString());
          mToDoAdapter.notifyDataSetChanged();
          mInputManager.hideSoftInputFromWindow(mEditTextTodo.getWindowToken(), 0);
          hideEditText(mRevealView);
          mAddButton.setImageResource(R.drawable.icn_morph_reverse);
          mAnimatable = (Animatable) (mAddButton).getDrawable();
          mAnimatable.start();
          applyRippleColor(mPalette.getVibrantColor(defaultColorForRipple),
                  mPalette.getDarkVibrantColor(defaultColorForRipple));
        }
    }
  }

  private void revealEditText(LinearLayout view) {
    int cx = view.getRight() - 30;
    int cy = view.getBottom() - 60;
    int finalRadius = Math.max(view.getWidth(), view.getHeight());
    Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
    view.setVisibility(View.VISIBLE);
    isEditTextVisible = true;
    anim.start();
  }

  private void hideEditText(final LinearLayout view) {
    int cx = view.getRight() - 30;
    int cy = view.getBottom() - 60;
    int initialRadius = view.getWidth();
    Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
    anim.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        view.setVisibility(View.INVISIBLE);
      }
    });
    isEditTextVisible = false;
    anim.start();
  }

  @Override
  public void onBackPressed() {
    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
    alphaAnimation.setDuration(100);
    mAddButton.startAnimation(alphaAnimation);
    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        mAddButton.setVisibility(View.GONE);
        finishAfterTransition();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
  }

  public void prepareOARData(){
    int sectionNumber = 0;
    for (OARTemplate oarTemplate :this.OARTemplateList){
      if(oarTemplate.getLateralized().equals("1")){
        this.allIncludedList.add(new EntryItem(oarTemplate.getLocation(), sectionNumber));
      } else {
        this.allIncludedList.add(new MedianItem(oarTemplate.getLocation(), sectionNumber));
      }

      }
    }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(LocaleHelper.onAttach(base));
  }


  public interface Item {
    public boolean isSection();
    public String getTitle();
    public int getSectionNumber();
    public boolean isMedian();
  }

  /**
   * Section Item
   */
  public class SectionItem implements Item {
    private final String title;
    public final int sectionNumber;

    public int getSectionNumber() {
      return sectionNumber;
    }


    public SectionItem(String title, int sectionNumber) {
      this.title = title;
      this.sectionNumber = sectionNumber;
    }

    public String getTitle() {
      return title;
    }

    @Override
    public boolean isSection() {
      return true;
    }

    @Override
    public boolean isMedian() {
      return false;
    }
  }

  public class MedianItem implements Item {
    private final String title;
    public final int sectionNumber;

    public int getSectionNumber() {
      return sectionNumber;
    }


    public MedianItem(String title, int sectionNumber) {
      this.title = title;
      this.sectionNumber = sectionNumber;
    }

    public String getTitle() {
      return title;
    }

    @Override
    public boolean isSection() { return false;
    }

    @Override
    public boolean isMedian() {
      return true;
    }
  }

  /**
   * Entry Item
   */
  public class EntryItem implements Item {
    public final String title;
    public final int sectionNumber;


    public int getSectionNumber() {
      return sectionNumber;
    }


    public EntryItem(String title, int sectionNumber) {
      this.title = title;
      this.sectionNumber = sectionNumber;
    }

    public String getTitle() {
      return title;
    }

    @Override
    public boolean isSection() {
      return false;
    }

    @Override
    public boolean isMedian() {
      return false;
    }

  }

}
