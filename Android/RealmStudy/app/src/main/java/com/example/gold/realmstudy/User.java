package com.example.gold.realmstudy;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Gold on 2017. 3. 22..
 */

public class User extends RealmObject {

    @PrimaryKey @Index
    Realm realm;
    public String name;

    public boolean hasLongName(){
        return name.length() > 7;

//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                Dog myDog = realm.createObject(Dog.class);
//                myDog.setName("fido");
//                myDog.setAge("1");
//            }
//        });
//        Dog myDog = realm.where(Dog.class).equalTo("age", "1").findFirst();

    }

    @Override
    public boolean equals(Object obj) {
        // custom
        return true;
    }

    public class Dog extends RealmObject{
        String name;
        String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

}
