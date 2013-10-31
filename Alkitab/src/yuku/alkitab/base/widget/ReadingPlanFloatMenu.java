package yuku.alkitab.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import yuku.afw.V;
import yuku.alkitab.base.S;
import yuku.alkitab.base.ac.ReadingPlanActivity;
import yuku.alkitab.base.util.ReadingPlanManager;
import yuku.alkitab.debug.R;

public class ReadingPlanFloatMenu extends LinearLayout {

	private long id;
	private int dayNumber;
	private int[] ariRanges;
	private boolean[] readReadings;
	private int sequence;

	private ReadingPlanFloatMenuClickListener leftNavigation;
	private ReadingPlanFloatMenuClickListener rightNavigation;
	private ReadingPlanFloatMenuClickListener readMark;

	private TextView tDescription;
	private ImageButton bLeft;
	private ImageButton bRight;
	private ImageButton bTick;

	public ReadingPlanFloatMenu(final Context context) {
		super(context);
		prepareLayout(context);
	}

	public ReadingPlanFloatMenu(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		prepareLayout(context);
	}

	public void load(long readingPlanId, int dayNumber, int[] ariRanges, boolean[] readReadings, int sequence) {
		this.id = readingPlanId;
		this.dayNumber = dayNumber;
		this.ariRanges = ariRanges;
		this.readReadings = readReadings;
		this.sequence = sequence;
		updateLayout();
	}

	private void prepareLayout(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.float_menu_reading_plan, this, true);

		tDescription = V.get(view, R.id.tDescription);
		bLeft = V.get(view, R.id.bNavLeft);
		bRight = V.get(view, R.id.bNavRight);
		bTick = V.get(view, R.id.bTick);

		bLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (sequence != 0) {
					sequence += -2;
					leftNavigation.onClick(ariRanges[sequence]);
					updateLayout();
				}
			}
		});

		bRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (sequence != ariRanges.length - 2) {
					sequence += 2;
					rightNavigation.onClick(ariRanges[sequence]);
					updateLayout();
				}
			}
		});

		bTick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				readReadings[sequence] = !readReadings[sequence];
				readReadings[sequence + 1] = readReadings[sequence];

				ReadingPlanManager.insertReadingPlanProgress(id, dayNumber, sequence);
				updateLayout();
			}
		});
	}

	private void updateLayout() {

		if (ariRanges == null || readReadings == null) {
			return;
		}

		if (readReadings[sequence]) {
			bTick.setImageResource(R.drawable.ic_checked);
		} else {
			bTick.setImageResource(R.drawable.ic_unchecked);
		}

		tDescription.setText(ReadingPlanActivity.getReference(S.activeVersion, new int[] {ariRanges[sequence], ariRanges[sequence + 1]}));
		if (sequence == 0) {
			bLeft.setEnabled(false);
			bRight.setEnabled(true);
		} else if (sequence == ariRanges.length - 2) {
			bLeft.setEnabled(true);
			bRight.setEnabled(false);
		} else {
			bLeft.setEnabled(true);
			bRight.setEnabled(true);
		}
	}

	public void setLeftNavigationClickListener(final ReadingPlanFloatMenuClickListener leftNavigation) {
		this.leftNavigation = leftNavigation;
	}

	public void setRightNavigationClickListener(final ReadingPlanFloatMenuClickListener rightNavigation) {
		this.rightNavigation = rightNavigation;
	}

	public void setReadMarkClickListener(final ReadingPlanFloatMenuClickListener readMark) {
		this.readMark = readMark;
	}

	public interface ReadingPlanFloatMenuClickListener {
		public void onClick(int ari);
	}

}
