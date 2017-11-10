import React, { Component } from "react";
import { View, Text, StyleSheet } from "react-native";

export default class TaskDetails extends Component {
    static navigationOptions = {
        title: "Details",
        headerStyle: { backgroundColor: "#e9006c" },
        headerTitleStyle: { color: "white" }
        };
    constructor(props) {
        super(props);
    }
    render() {
        const { state } = this.props.navigation;
        var task = state.params ? state.params.task : "<undefined>";
        return (
            <View style={styles.container}>
                <Text style={styles.text}>{task.title}</Text>
                <Text style={styles.text}>{task.status}</Text>
                <Text style={styles.text}>{task.date}</Text>

            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#e91254"
    },
    text: {
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#e91254",
        color: "white",
        fontSize: 30
    },
});

