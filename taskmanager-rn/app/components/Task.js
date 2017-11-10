import React from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity,} from 'react-native';

export default class Task extends React.Component {
    render() {
        return (
            <View key={this.props.keyval} style={styles.note}>
                <Text style={styles.taskText}>{this.props.val.task}</Text>
                <Text style={styles.taskText}>{this.props.val.status}</Text>
                <Text style={styles.taskText}>{this.props.val.date}</Text>
            </View>

        );
    }
}

const styles = StyleSheet.create({
    note:{
        position:'relative',
        padding:20,
        paddingRight:100,
        borderBottomWidth:2,
        borderBottomColor:'#ededed',
    },
    taskText:{
        paddingLeft:20,
        borderLeftWidth:10,
        borderLeftColor:'#E91E63',
    }
});
