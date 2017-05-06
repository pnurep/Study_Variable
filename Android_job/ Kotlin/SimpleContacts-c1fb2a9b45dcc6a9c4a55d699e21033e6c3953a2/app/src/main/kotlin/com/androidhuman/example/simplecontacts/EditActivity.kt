package com.androidhuman.example.simplecontacts

import com.androidhuman.example.simplecontacts.model.Person

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.androidhuman.example.simplecontacts.model.PRIMARY_KEY

import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*

@JvmField
val REQUEST_CODE = 10 //패키지레벨

class EditActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        intent.extras?.run {
            getLong(PRIMARY_KEY)
        }

        intent.extras?.run {

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.menu_activity_edit_done == item.itemId) {
            applyChanges()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun applyChanges() {
        if (et_activity_edit_name.text.isEmpty()) {
            // TODO: Remove error on content changes
            ti_activity_edit_name.error = getText(R.string.msg_name_cannot_be_empty)
            return
        }

        val realm = Realm.getDefaultInstance()

        // Get next id value for primary key
        val currentMaxId = realm.where(Person::class.java).max(PRIMARY_KEY)
        val nextId: Long = if (null == currentMaxId) 0L else currentMaxId.toLong() + 1



        realm.beginTransaction()

        val person = realm.createObject(Person::class.java, nextId).apply {
            name = et_activity_edit_name.text.toString()
            address = et_activity_edit_address.text.toString()
        }

//        person.let {
//
//        }
//
//        with(person) {
//
//        }
//
//        person.run {
//
//        }

        /* Alternative method:
        Person person = new Person();
        person.setId(nextId);
        person.setName(etName.getText().toString());
        person.setAddress(etAddress.getText().toString());
        realm.copyToRealm(person);
        */

        realm.commitTransaction()

        realm.close()

        setResult(RESULT_OK)
        finish()

    }

//    companion object {
//
//        //public static final int REQUEST_CODE = 10
//        //코틀린은 static키워드가 없다.
//        val REQUEST_CODE = 10
//    }

    private fun fetchData(id: Long){
        val realm = Realm.getDefaultInstance()

        val person = checkNotNull(
                realm.where(Person::class.java).equalTo(PRIMARY_KEY, id).findFirst())

        et_activity_edit_name.setText(person.name)
        et_activity_edit_address.setText(person.address)
    }



}
