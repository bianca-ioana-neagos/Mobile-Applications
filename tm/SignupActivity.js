import React, { Component } from 'react';
import {StyleSheet,Switch,Text, TextInput, TouchableOpacity, View} from 'react-native';


class SignupActivity extends React.Component {
    static navigationOptions = {
        headerStyle: { backgroundColor: "#e9b7dc" },
        title:'Signup',
    };
    constructor(props) {
        super(props);
        const {state} = this.props.navigation;
        this.state = {
            email: "",
            password: "",
            manager: false,
        };
        this.dbRef = global.firebaseApp.database().ref().child('users');
        this.auth = global.firebaseApp.auth();
        this.auth.onAuthStateChanged((user)=>{
          if(user){
            this.dbRef.child(user.email.replace(/\./g, ',')).set({
              email :this.state.email,
              password: this.state.password,
              manager: this.state.manager,
              tasks: []
            })
          }
        })
    }

    render() {
        return (
            <View style={styles.container}>
                <View style={styles.header}>
                    <Text style={styles.headerText}>Task Manager</Text>
                </View>

                <TextInput
                    style={styles.textInput}
                    onChangeText={(email) => this.setState({email})}
                />
                <TextInput
                    style={styles.textInput}
                    onChangeText={(password) => this.setState({password})}
                />
                <Text style={styles.header1Text}> Manager</Text>
                <Switch style={styles.swtc}
                  value={this.state.manager}
                  onValueChange={(isManager) => this.setState({manager: isManager}) }
                />
                <TouchableOpacity onPress={() =>{
                    this.auth.createUserWithEmailAndPassword(this.state.email, this.state.password);
                    this.props.navigation.navigate("Home", {email: this.state.email});
                }} style={styles.signupButton}>
                    <Text style={styles.signupButtonText}>SignUp</Text>
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
    header1Text: {
        alignSelf: 'center',
        fontSize: 20,
        padding: 26,
    },
    swtc:{
        alignSelf: 'center',
    },

    footer:{
        alignItems:'center',
        left:0,
        right:0,
    },

    signupButton: {
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
      top:50,
      left:250,

    },
    signupButtonText:{
        color:'#fff',
        fontSize:24,
    },
    textInput:{
        alignSelf:'stretch',
        color:'#fff',
        fontSize:20,
        padding:20,
        paddingTop:20,
        backgroundColor:'#252225',
        borderTopWidth:2,
        borderTopColor:'#ededed',
    },



});
export default SignupActivity;
