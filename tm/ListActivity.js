import React, { Component } from 'react';
import {
    Platform,
    StyleSheet,
    Text, TextInput, TouchableOpacity,
    View, Share, FlatList, Alert,
    AsyncStorage,ListView,
} from 'react-native';


class ListActivity extends React.Component {

    static navigationOptions = {
        headerStyle: { backgroundColor: "#e9b7dc" },
        title: 'List of tasks',
    };

    constructor(props) {
        super(props);
        this.state = {
            ds: global.ds.cloneWithRows([]),
            email: this.props.navigation.state.params.email
        };
        // AsyncStorage.getAllKeys().then((keys) => {
        //     tasks = [];
        //     for (keyIndex in keys) {
        //         AsyncStorage.getItem(keys[keyIndex]).then((value) => {
        //             tasks.push(JSON.parse(value));
        //             this.setState({ds: global.ds.cloneWithRows(tasks)});
        //         });
        //     }
        // });
        this.ref = global.firebaseApp.database().ref().child('users').child(this.state.email.replace(/\./g, ',')).child('tasks');
        this.auth = global.firebaseApp.auth();
        this.updateState();
    }

    updateState(){
        // AsyncStorage.getAllKeys().then((keys) => {
        //     tasks = [];
        //     for(keyIndex in keys){
        //         AsyncStorage.getItem(keys[keyIndex]).then((value) => {
        //             tasks.push(JSON.parse(value));
        //             this.setState({ds: global.ds.cloneWithRows(tasks)});
        //         })
        //     }
        //
        // });
        this.ref.on('value', (snap) => {
          var taskList = [];
          snap.forEach(element => {
            taskList.push(element.val())
          });
          console.log(taskList);
          this.setState({ds: global.ds.cloneWithRows(taskList)});
        })
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
        Share.share({message: this.state.email});
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
    cell: {
        flex: 1,
        margin: 5,
        backgroundColor: "rgba(255,255,255,0.5)",
        height: 50,
        fontSize:20,
    },
    addButton:{
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
    addButtonText:{
        color:'#fff',
        fontSize:24,
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
      top: -7,

    },
    sendButtonText:{
        color:'#fff',
        fontSize:24,
    },

});

export default ListActivity;
