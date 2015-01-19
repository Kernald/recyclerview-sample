package fr.enoent.recyclerviewsample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements Adapter.ViewHolder.ClickListener {
	@SuppressWarnings("unused")
	private static final String TAG = MainActivity.class.getSimpleName();

	private Adapter adapter;
	private ActionModeCallback actionModeCallback = new ActionModeCallback();
	private ActionMode actionMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new Adapter(this);

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
	}

	@Override
	public void onItemClicked(int position) {
		if (actionMode != null) {
			toggleSelection(position);
		} else {
			adapter.removeItem(position);
		}
	}

	@Override
	public boolean onItemLongClicked(int position) {
		if (actionMode == null) {
			actionMode = startSupportActionMode(actionModeCallback);
		}

		toggleSelection(position);

		return true;
	}

	/**
	 * Toggle the selection state of an item.
	 *
	 * If the item was the last one in the selection and is unselected, the selection is stopped.
	 * Note that the selection must already be started (actionMode must not be null).
	 *
	 * @param position Position of the item to toggle the selection state
	 */
	private void toggleSelection(int position) {
		adapter.toggleSelection(position);
		int count = adapter.getSelectedItemCount();

		if (count == 0) {
			actionMode.finish();
		} else {
			actionMode.setTitle(String.valueOf(count));
			actionMode.invalidate();
		}
	}

	private class ActionModeCallback implements ActionMode.Callback {
		@SuppressWarnings("unused")
		private final String TAG = ActionModeCallback.class.getSimpleName();

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.getMenuInflater().inflate (R.menu.selected_menu, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
				case R.id.menu_remove:
					adapter.removeItems(adapter.getSelectedItems());
					mode.finish();
					return true;

				default:
					return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			adapter.clearSelection();
			actionMode = null;
		}
	}
}
