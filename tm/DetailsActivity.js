import React, { Component } from 'react';
import {
    Platform,
    StyleSheet,Alert,
    Text, TextInput, TouchableOpacity,
    View, Share, FlatList, Button,AsyncStorage,
} from 'react-native';
import DatePicker from 'react-native-datepicker';

class DetailsActivity extends React.Component {
    static navigationOptions = {
        headerStyle: { backgroundColor: "#e9b7dc" },
        title:'Details',
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
                    AsyncStorage.mergeItem(this.props.navigation.state.params.obj.name, JSON.stringify({
                        name: this.state.name,
                        status: this.state.status,
                        dueDate: this.state.dueDate
                    })).then(()=>{
                        this.props.navigation.state.params.updateState();
                        this.props.navigation.goBack();})

                }} style={styles.addButton}>
                    <Text style={styles.addButtonText}>Save</Text>
                </TouchableOpacity>

                <TouchableOpacity  style={styles.deleteButton}
                                   onPress={()=> {
                                       Alert.alert(
                                           'Alert',
                                           'Are you sure you want to delete?',
                                           [
                                               {text: 'Cancel', onPress: () => console.log('Canceled'), style: 'cancel'},
                                               {
                                                   text: 'Delete', onPress: () => {

                                                   AsyncStorage.removeItem(this.props.navigation.state.params.obj.name).then(() => {
                                                       this.props.state.updateState();
                                                       this.props.navigation.goBack();
                                                   });
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
    deleteButton:{
        position:'absolute',
        backgroundColor:'#28e9b1',
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
export default DetailsActivity;