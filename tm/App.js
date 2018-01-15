/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */
import ListActivity from './ListActivity';
import DetailsActivity from './DetailsActivity';
import AddActivity from './AddActivity';
import LoginActivity from './LoginActivity';
import SignupActivity from './SignupActivity';
import React, {Component} from 'react';
import {
    Platform,
    StyleSheet,
    Text, TextInput, TouchableOpacity,
    View, Share, ListView,
} from 'react-native';

import {
    StackNavigator,
} from "react-navigation";
import Pie from 'react-native-pie';
import * as firebase from 'firebase';
import * as PushNotification from 'react-native-push-notification';

console.disableYellowBox = true;

const config = {
    apiKey: "AIzaSyAUbaJ_1fd7TP-Q1-0SVQyaKWkltrf76f4",
    authDomain: "taskmanegerreact.firebaseapp.com",
    databaseURL: "https://taskmanegerreact.firebaseio.com",
    projectId: "taskmanegerreact",
    storageBucket: "taskmanegerreact.appspot.com",
    messagingSenderId: "816024357740"
  };
global.firebaseApp = firebase.initializeApp(config);

global.tasks=[{name:'task1',status:'to do', dueDate:new Date()},{name:'task2',status:'in progress', dueDate:new Date()},{name:'task3',status:'done', dueDate:new Date()}];
global.ds = new ListView.DataSource({rowHasChanged:(r1,r2)=>r1 !==r2});

data=[];
toDo=[];
done=[];
inProgr=[];
for(i=0;i<ds.length;i++){
    if(ds[i].status =='to do'){
        toDo.push([ds[i]])
    }
    if(ds[i].status =='done'){
        done.push([ds[i]])
    }
    if(ds[i].status =='in progress'){
        inProgr.push([ds[i]])
    }
}
toDoP=(toDo.length *100)/3;
doneP=(done.length*100)/3;
inProgrP=(inProgr.length*100)/3;

class HomeActivity extends React.Component {
    static navigationOptions = {
        headerStyle: { backgroundColor: "#e9b7dc" },
        title:'Home',
    };

    notifications(){}

    render() {
        this.notifications();
        const { navigate } = this.props.navigation;
        return (
            <View style={styles.container}>
                <View style={styles.header}>
                    <Text style={styles.headerText}>Task Manager</Text>
                </View>
                <View style={styles.chart}>
                <Pie
                radius={100}
                innerRadius={30}
                series={[20, 30, 50]}
                    colors={['#308be9', '#e95bd7', '#e95408']}
                />

                </View>
                <TouchableOpacity onPress={() => navigate("List", {email: this.props.navigation.state.params.email})} style={styles.addButton}>
                    <Text style={styles.addButtonText}>Tasks</Text>
                </TouchableOpacity>

                <TouchableOpacity onPress={() => {
                    this.auth.signOut()
                    this.props.navigation.goBack();
                }} style={styles.logoutButton}>
                    <Text style={styles.logoutButtonText}>LogOut</Text>
                </TouchableOpacity>


            </View>


        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    header: {
        backgroundColor: '#E91E63',
        alignItems: 'center',
        justifyContent: 'center',
        borderBottomWidth: 10,
        borderBottomColor: '#ddd',
    },
    headerText: {
        color: 'white',
        fontSize: 40,
        padding: 26,
    },
    footer: {
        alignItems: 'center',
        left: 0,
        right: 0,
    },
    chart:{
        alignItems:'center',
        top:60,

    },
    addButton: {
      position: 'relative',
      backgroundColor: '#E91E63',
      width: 90,
      height: 90,
      borderRadius: 50,
      borderColor: '#ccc',
      alignItems: 'center',
      justifyContent: 'center',
      elevation: 8,
      bottom: 30,
      right: 20,
      zIndex: 10,
      top:80,
      left:250,

    },
    addButtonText: {
        color: '#fff',
        fontSize: 24,
    },
    logoutButton:{
      position: 'relative',
      backgroundColor:'#308be9',
      width:90,
      height:90,
      borderRadius:50,
      borderColor:'#ccc',
      alignItems: 'center',
      justifyContent:'center',
      elevation:8,
      bottom: 30,
      left:20,
      zIndex:10,
      top: -5,

    },
    logoutButtonText:{
        color:'#fff',
        fontSize:24,
    },


});

const App = StackNavigator({
    Login: {screen: LoginActivity},
    Home: {screen: HomeActivity},
    List: {screen: ListActivity},
    Add: { screen:AddActivity},
    Details: {screen: DetailsActivity},
    Signup: {screen: SignupActivity},

});
export default App;
