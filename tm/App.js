/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */
import ListActivity from './ListActivity';
import DetailsActivity from './DetailsActivity';
import AddActivity from './AddActivity';
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



global.tasks=[
    {
        name:'task1',
        status:'to do',
        dueDate:new Date()
    },
    {
        name:'task2',
        status:'in progress',
        dueDate:new Date()
    },
    {
        name:'task3',
        status:'done',
        dueDate:new Date()
    }
];
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

    render() {
        const { navigate } = this.props.navigation;
        return (
            <View style={styles.container}>
                <View style={styles.header}>
                    <Text style={styles.headerText}>Task Manager</Text>
                </View>
                <View style={styles.chart}>
                <Pie
                radius={150}
                innerRadius={50}
                series={[20, 30, 50]}
                    colors={['#308be9', '#e95bd7', '#e95408']}
                />

                </View>
                <TouchableOpacity onPress={() => navigate("List")} style={styles.addButton}>
                    <Text style={styles.addButtonText}>Tasks</Text>
                </TouchableOpacity>

                <View style={styles.circle1}></View>
                <View style={styles.circle2} ></View>
                <View style={styles.circle3} ></View>

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
    circle1: {
        width: 20,
        height: 20,
        borderRadius: 20/2,
        backgroundColor: '#308be9',
        left:20,
        bottom: -100,
    },
    circle2: {
        width: 20,
        height: 20,
        borderRadius: 20/2,
        backgroundColor: '#e95bd7',
        left:20,
        bottom: -100,
    },
    circle3: {
        width: 20,
        height: 20,
        borderRadius: 20/2,
        backgroundColor: '#e95408',
        left:20,
        bottom: -100,
    },
    addButton: {
        position: 'absolute',
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

    },
    addButtonText: {
        color: '#fff',
        fontSize: 24,
    },


});
global.tasks=[{name:'task1',status:'to do', dueDate:new Date()},{name:'task2',status:'in progress', dueDate:new Date()},{name:'task3',status:'done', dueDate:new Date()}];
global.ds = new ListView.DataSource({rowHasChanged:(r1,r2)=>r1 !==r2});
const App = StackNavigator({
    Home: {screen: HomeActivity},
    List: {screen: ListActivity},
    Add: { screen:AddActivity},
    Details: {screen: DetailsActivity},
});
export default App;