import React, { Component } from 'react';
import {
    Platform,
    StyleSheet,
    Text, TextInput, TouchableOpacity,
    View, Share, FlatList, Alert,
    AsyncStorage,
} from 'react-native';
import {Repo} from './Repo';
import {Task} from "./Task";


class ListActivity extends React.Component {

    static navigationOptions = {
        headerStyle: { backgroundColor: "#e9b7dc" },
        title: 'List of tasks',
    };
    onRefresh() {
        this.setState({refreshing: true});
        this.setState({refreshing: false});
    }
    constructor(props){
        super(props);
        this.state = {
            refreshing: false,
        };
        this.onRefresh = this.onRefresh.bind(this);
        this.repo = new Repo();
    }

    render() {
        const { navigate } = this.props.navigation;

        let elems = this.repo.tasks;
        let data = [];
        for (let i = 0; i < elems.length; i++) {
            data.push({key: i, value: elems[i]});
        }
        return (
            <View style={styles.container}>
                <View style={styles.footer}>

                    <TextInput style={styles.textInput}
                               onChangeText={(taskText)=>this.setState({name: taskText})}value={this.state.taskText}
                               placeholder='>task' placeholderTextColour='white'>
                    </TextInput>

                </View>
                <FlatList
                    data={data}
                    renderItem={({item})=>
                        <View>
                            <Text style={styles.cell}
                                onPress={() => navigate('Details', {
                                    obj: item.value,
                                    tasks: this.repo.tasks,
                                    refreshing: this.onRefresh,
                                })}>{item.value.name}

                                </Text>
                            <TouchableOpacity  style={styles.deleteButton}
                                               onPress={()=> {
                                                   Alert.alert(
                                                       'Alert',
                                                       'Are you sure you want to delete?',
                                                       [
                                                           {text: 'Cancel', onPress: () => console.log('Canceled'), style: 'cancel'},
                                                           {
                                                               text: 'Delete', onPress: () => {

                                                               elems.splice(this.props.index, 1);
                                                               AsyncStorage.removeItem(this.props.index);
                                                               this.onRefresh();
                                                           }
                                                           },
                                                       ],
                                                       {cancelable: true}
                                                   );
                                               }
                                               }
                            >
                                <Text style={styles.addButtonText}>-</Text>
                            </TouchableOpacity>
                            <Text></Text>
                        </View>
                    }

                />

                <TouchableOpacity onPress={() => navigate('Add', {
                    obj: new Task('','',''),
                    tasks: this.repo.tasks,
                    refreshing: this.onRefresh,
                })} style={styles.addButton}>
                    <Text style={styles.addButtonText}>+</Text>
                </TouchableOpacity>

                <TouchableOpacity onPress={this.sendTask.bind(this)} style={styles.sendButton}>
                    <Text style={styles.sendButtonText}>@</Text>
                </TouchableOpacity>
            </View>

        );
    }

    addTask(){
        if(this.state.taskText){
            var d = new Date();
            this.repo.add({value: new Task(this.state.taskText,'To Do', d.getFullYear()+'/'+(d.getMonth()+1)+'/'+d.getDate())});
            this.setState({taskArray:this.state.taskArray})
            this.setState({taskText:''});
        }
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