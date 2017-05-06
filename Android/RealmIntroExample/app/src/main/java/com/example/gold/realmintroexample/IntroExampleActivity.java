package com.example.gold.realmintroexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gold.realmintroexample.model.Cat;
import com.example.gold.realmintroexample.model.Dog;
import com.example.gold.realmintroexample.model.Person;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class IntroExampleActivity extends AppCompatActivity {

    public static final String TAG = IntroExampleActivity.class.getName();
    private LinearLayout rootLayout = null;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_example);

        rootLayout = (LinearLayout) findViewById(R.id.container);
        rootLayout.removeAllViews();

        realm = Realm.getDefaultInstance();

        basicCRUD(realm);
        basicQuery(realm);
        basicLinkQuery(realm);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String info;
                info = complexReadWrite();
                info += complexQuery();
                return info;
            }

            @Override
            protected void onPostExecute(String result) {
                showStatus(result);
            }
        }.execute();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void showStatus(String txt){
        Log.i(TAG, txt);
        TextView tv = new TextView(this);
        tv.setText(txt);
        rootLayout.addView(tv);
    }

    private void basicLinkQuery(Realm realm) {
        showStatus("\nPerforming basic Link Query operation...");
        showStatus("number of persons : " + realm.where(Person.class).count());

        RealmResults<Person> result = realm.where(Person.class).equalTo("cats.name", "Tiger").findAll();
        showStatus("Size Of result set : " + result.size());

    }

    private void basicQuery(Realm realm) {
        showStatus("\nPerforming basic Query operation...");
        showStatus("number of persons : " + realm.where(Person.class).count());

        RealmResults<Person> result = realm.where(Person.class).equalTo("age", 99).findAll();

        showStatus("Size of result set: " + result.size());

    }

    private void basicCRUD(Realm realm) {
        showStatus("Perform basic Create/Read/Update/Delete (CRUD) operations...");

        //create
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class);
                person.setId(1);
                person.setName("Young Person");
                person.setAge(14);
            }
        });

        final Person person = realm.where(Person.class).findFirst();
        showStatus(person.getName() + ":" + person.getAge());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                person.setName("Senior Person");
                person.setAge(99);
                showStatus(person.getName() + " got older: " + person.getAge());
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Person.class);
            }
        });
    }


    private String complexReadWrite() {
        String status = "\nPerforming complex Read/Write operation...";

        // Open the default realm. All threads must use its own reference to the realm.
        // Those can not be transferred across threads.
        Realm realm = Realm.getDefaultInstance();

        // Add ten persons in one transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog fido = realm.createObject(Dog.class);
                fido.name = "fido";
                for (int i = 0; i < 10; i++) {
                    Person person = realm.createObject(Person.class);
                    person.setId(i);
                    person.setName("Person no. " + i);
                    person.setAge(i);
                    person.setDog(fido);

                    // The field tempReference is annotated with @Ignore.
                    // This means setTempReference sets the Person tempReference
                    // field directly. The tempReference is NOT saved as part of
                    // the RealmObject:
                    person.setTempReference(42);

                    for (int j = 0; j < i; j++) {
                        Cat cat = realm.createObject(Cat.class);
                        cat.name = "Cat_" + j;
                        person.getCats().add(cat);
                    }
                }
            }
        });

        // Implicit read transactions allow you to access your objects
        status += "\nNumber of persons: " + realm.where(Person.class).count();

        // Iterate over all objects
        for (Person pers : realm.where(Person.class).findAll()) {
            String dogName;
            if (pers.getDog() == null) {
                dogName = "None";
            } else {
                dogName = pers.getDog().name;
            }
            status += "\n" + pers.getName() + ":" + pers.getAge() + " : " + dogName + " : " + pers.getCats().size();
        }

        // Sorting
        RealmResults<Person> sortedPersons = realm.where(Person.class).findAllSorted("age", Sort.DESCENDING);
        status += "\nSorting " + sortedPersons.last().getName() + " == " + realm.where(Person.class).findFirst()
                .getName();

        realm.close();
        return status;
    }

    private String complexQuery() {
        String status = "\n\nPerforming complex Query operation...";

        Realm realm = Realm.getDefaultInstance();
        status += "\nNumber of persons: " + realm.where(Person.class).count();

        // Find all persons where age between 7 and 9 and name begins with "Person".
        RealmResults<Person> results = realm.where(Person.class)
                .between("age", 7, 9)       // Notice implicit "and" operation
                .beginsWith("name", "Person").findAll();
        status += "\nSize of result set: " + results.size();

        realm.close();
        return status;
    }









}
