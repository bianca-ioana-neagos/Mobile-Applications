import React from 'react';
import { StyleSheet, Text, View, TextInput, ScrollView, TouchableOpacity,Share,} from 'react-native';
import Task from'./app/components/Task'

export default class TaskList extends React.Component {

    state = {
        taskArray:[{'task': 'task1', 'status':'test', 'date':'test'}],
        taskText:'',
    }
    render() {

        let tasks= this.state.taskArray.map((val,key) => {
            return <Task key={key} keyval={key} val={val} />
        });
        return (
            <View style={styles.container}>
                <View style={styles.header}>
                    <Text style={styles.headerText}>Task Manager</Text>
                </View>
                <View style={styles.footer}>

                    <TextInput style={styles.textInput}
                               onChangeText={(taskText)=>this.setState({taskText})}value={this.state.taskText}
                               placeholder='>task' placeholderTextColour='white'>
                    </TextInput>

                </View>
                <ScrollView style={styles.scrollContainer}>
                    {tasks}
                </ScrollView>

                <TouchableOpacity onPress={this.addTask.bind(this)} style={styles.addButton}>
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
            this.state.taskArray.push({'task':this.state.taskText,'status': 'To Do', 'date':d.getFullYear()+'/'+(d.getMonth()+1)+'/'+d.getDate()});
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
        header:{
            backgroundColor:'#E91E63',
            alignItems: 'center',
            justifyContent: 'center',
            borderBottomWidth: 10,
            borderBottomColor:'#ddd',
        },
        headerText:{
            color: 'white',
            fontSize:18,
            padding:26,
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

        addButton:{
            position:'absolute',
            backgroundColor:'#E91E63',
            width:90,
            height:90,
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
        textInput:{
            alignSelf:'stretch',
            color:'#fff',
            padding:20,
            paddingTop:46,
            backgroundColor:'#252525',
            borderTopWidth:2,
            borderTopColor:'#ededed',
        },
        sendButton:{
            position:'absolute',
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

        },
        sendButtonText:{
            color:'#fff',
            fontSize:24,
        },

    });