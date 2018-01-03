import React, { Component } from 'react';
import {
    Platform,
    StyleSheet,
    Text, TextInput, TouchableOpacity,
    View, Share, FlatList, Alert,
    AsyncStorage,ListView,
} from 'react-native';
import {Repo} from './Repo';
import {Task} from "./Task";


class ListActivity extends React.Component {

    static navigationOptions = {
        headerStyle: { backgroundColor: "#e9b7dc" },
        title: 'List of tasks',
    };

    constructor() {
        super();
        this.state = {
            ds: global.ds.cloneWithRows([]),
        };
        AsyncStorage.getAllKeys().then((keys) => {
            tasks = [];
            for (keyIndex in keys) {
                AsyncStorage.getItem(keys[keyIndex]).then((value) => {
                    tasks.push(JSON.parse(value));
                    this.setState({ds: global.ds.cloneWithRows(tasks)});
                });
            }
        });
    }

    updateState(){
        AsyncStorage.getAllKeys().then((keys) => {
            tasks = [];
            for(keyIndex in keys){
                AsyncStorage.getItem(keys[keyIndex]).then((value) => {
                    tasks.push(JSON.parse(value));
                    this.setState({ds: global.ds.cloneWithRows(tasks)});
                })
            }

        });
    }

    renderRow(record){
        return (
            <View>
                <Text style={styles.cell}
                      onPress={() => this.props.navigation.navigate('Details', {
                          obj: record,
                          updateState: this.updateState.bind(this)
                      })}>{record.name}

                </Text>


            </View>
        );
    }

    render() {



        return (
            <View style={styles.container}>
                <View style={styles.footer}>

                    <TextInput style={styles.textInput}
                               onChangeText={(taskText)=>this.setState({name: taskText})}value={this.state.taskText}
                               placeholder='>task' placeholderTextColour='white'>
                    </TextInput>

                </View>
                <ListView
                    dataSource={this.state.ds}
                    renderRow={this.renderRow.bind(this)}

                />

                <TouchableOpacity onPress={() => this.props.navigation.navigate('Add', {updateState: this.updateState.bind(this)})} style={styles.addButton}>
                    <Text style={styles.addButtonText}>+</Text>
                </TouchableOpacity>

                <TouchableOpacity onPress={this.sendTask.bind(this)} style={styles.sendButton}>
                    <Text style={styles.sendButtonText}>@</Text>
                </TouchableOpacity>
            </View>

        );
    }


    sendTask(){
        Share.share({message: this.state.taskText});
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    scrollContainer: {
        flex: 1,
        marginBottom: 100,
    },
    footer:{
        alignItems:'center',
        left:0,
        right:0,
    },
    cell: {
        flex: 1,
        margin: 5,
        backgroundColor: "rgba(255,255,255,0.5)",
        height: 50,
        fontSize:20,
    },
    addButton:{
        position:'absolute',
        backgroundColor:'#E91E63',
        width:80,
        height:80,
        borderRadius:50,
        borderColor:'#ccc',
        alignItems: 'center',
        justifyContent:'center',
        elevation:8,
        bottom: 30,
        right:20,
        zIndex:10,

    },
    addButtonText:{
        color:'#fff',
        fontSize:24,
    },
    deleteButton:{
        position:'absolute',
        backgroundColor:'#28e9b1',
        width:30,
        height:30,
        borderRadius:50,
        borderColor:'#ccc',
        alignItems: 'center',
        justifyContent:'center',
        elevation:8,
        bottom: 20,
        right:20,
        zIndex:10,

    },
    textInput:{
        alignSelf:'stretch',
        color:'#fff',
        fontSize:20,
        padding:20,
        paddingTop:46,
        backgroundColor:'#252225',
        borderTopWidth:2,
        borderTopColor:'#ededed',
    },
    sendButton:{
        position:'absolute',
        backgroundColor:'#308be9',
        width:80,
        height:80,
        borderRadius:50,
        borderColor:'#ccc',
        alignItems: 'center',
        justifyContent:'center',
        elevation:8,
        bottom: 30,
        left:20,
        zIndex:10,

    },
    sendButtonText:{
        color:'#fff',
        fontSize:24,
    },

});

export default ListActivity;