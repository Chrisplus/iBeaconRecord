package com.chrisplus.beaconrecorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aprilbrother.aprilbrothersdk.Beacon;

public class BeaconAdapter extends BaseAdapter {

	private ArrayList<Beacon> beacons;
	private LayoutInflater inflater;

	public static final String SENSINGTHREAD = "Sensing Thread";
	private FileOutputStream outstream;
	private File file;

	public BeaconAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.beacons = new ArrayList<Beacon>();

	}

	public void replaceWith(Collection<Beacon> newBeacons) {
		this.beacons.clear();
		this.beacons.addAll(newBeacons);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return beacons.size();
	}

	@Override
	public Beacon getItem(int position) {
		return beacons.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = inflateIfRequired(view, position, parent);
		bind(getItem(position), view);
		return view;
	}

	private void bind(Beacon beacon, View view) {
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.macTextView.setText(String.format("MAC: %s (%.2fm)",
				beacon.getMacAddress(), beacon.getDistance()));
		holder.uuidTextView.setText("UUID: " + beacon.getProximityUUID());
		holder.majorTextView.setText("Major: " + beacon.getMajor());
		holder.minorTextView.setText("Minor: " + beacon.getMinor());
		holder.measuredPowerTextView.setText("MPower: "
				+ beacon.getMeasuredPower());
		holder.rssiTextView.setText("RSSI: " + beacon.getRssi());

		String temp = System.currentTimeMillis() + ","
				+ beacon.getProximityUUID() + "," + beacon.getRssi() + ","
				+ beacon.getDistance() + "\r\n";
		try {
			if (outstream != null) {
				outstream.write(temp.getBytes());
				outstream.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private View inflateIfRequired(View view, int position, ViewGroup parent) {
		if (view == null) {
			view = inflater.inflate(R.layout.deviceitem, null);
			view.setTag(new ViewHolder(view));
		}
		return view;
	}

	public void flush() {
		if (outstream != null) {
			try {
				outstream.flush();
				outstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void record() {
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath() + "/Beacon_Log/");
		dir.mkdirs();
		file = new File(dir, System.currentTimeMillis() + ".csv");
	}

	static class ViewHolder {
		final TextView macTextView;
		final TextView uuidTextView;
		final TextView majorTextView;
		final TextView minorTextView;
		final TextView measuredPowerTextView;
		final TextView rssiTextView;

		ViewHolder(View view) {
			macTextView = (TextView) view.findViewWithTag("mac");
			uuidTextView = (TextView) view.findViewWithTag("uuid");
			majorTextView = (TextView) view.findViewWithTag("major");
			minorTextView = (TextView) view.findViewWithTag("minor");
			measuredPowerTextView = (TextView) view.findViewWithTag("mpower");
			rssiTextView = (TextView) view.findViewWithTag("rssi");
		}
	}

}
