import React, { Component } from 'react';
import {
    Platform,
    StyleSheet,
    Text, TextInput, TouchableOpacity,
    View, Share, FlatList, Button, AsyncStorage,
} from 'react-native';
import DatePicker from 'react-native-datepicker';
import {Task} from "./Task";


class AddActivity extends React.Component {
    static navigationOptions = {
        headerStyle: { backgroundColor: "#e9b7dc" },
        title:'Add',
    };
    constructor(props) {
        super(props);
        const {state} = this.props.navigation;
        this.state = {
            name: state.params.obj.name,
            status: state.params.obj.status,
            dueDate: state.params.obj.dueDate,
        };
    }

    render() {
        const {state} = this.props.navigation;
        const {goBack} = this.props.navigation;
        return (
            <View style={styles.container}>
                <TextInput
                    style={styles.textInput}
                    onChangeText={(text) => this.setState({name: text})}
                    value={this.state.name}
                />
                <TextInput
                    style={styles.textInput}
                    onChangeText={(text) => this.setState({status: text})}
                    value={this.state.status}
                />
                <DatePicker

                            date={this.state.dueDate}
                            mode="date"
                            placeholder="select date"
                            onDateChange={(date) => {this.setState({dueDate: date})}}
                />



                <TouchableOpacity onPress={() => {
                    AsyncStorage.setItem(this.state.name, JSON.stringify({
                        name: this.state.name,
                        status: this.state.status,
                        dueDate: this.state.dueDate
                    }));
                    state.params.obj.name = this.state.name;
                    state.params.obj.status = this.state.status;
                    state.params.obj.dueDate = this.state.dueDate;
                    state.params.tasks.push(new Task(this.state.name, this.state.status,this.state.dueDate));
                    state.params.refreshing();
                    goBack(null);


                }} style={styles.addButton}>
                    <Text style={styles.addButtonText}>Save</Text>
                </TouchableOpacity>

            </View>

        );
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
        height: 50
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
export default AddActivity;