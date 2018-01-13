import React, { Component } from 'react';
import {StyleSheet, Text, TextInput, TouchableOpacity, View, Alert} from 'react-native';


class LoginActivity extends React.Component {
    static navigationOptions = {
        headerStyle: { backgroundColor: "#e9b7dc" },
        title:'Login',
    };
    constructor(props) {
        super(props);
        const {state} = this.props.navigation;
        this.state = {
            email: "",
            password: "",
        };
        this.auth = global.firebaseApp.auth();
        this.auth.onAuthStateChanged((user) => {
            if(this.state.username != "" && this.state.password != ""){
              Alert.alert('Log in failed', 'Wrong username or password', [{text: 'OK', onPress: () => console.log('OK')}]);
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
                <TouchableOpacity onPress={() => {
                    this.auth.signInWithEmailAndPassword(this.state.email, this.state.password);
                     this.props.navigation.navigate("Home", {email: this.state.email});
                  }} style={styles.loginButton}>
                    <Text style={styles.loginButtonText}>LogIn</Text>
                </TouchableOpacity>

                <TouchableOpacity onPress={() => this.props.navigation.navigate("Signup")} style={styles.signupButton}>
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
    loginButton: {
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
        top:110,
        left:250,


    },
    loginButtonText:{
        color:'#fff',
        fontSize:24,
    },
    textInput:{
        alignSelf:'stretch',
        color:'#fff',
        fontSize:20,
        padding:20,
        paddingTop:30,
        backgroundColor:'#252225',
        borderTopWidth:2,
        borderTopColor:'#ededed',
    },
    signupButton:{
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
        top: 20,

    },
    signupButtonText:{
        color:'#fff',
        fontSize:24,
    },


});
export default LoginActivity;
