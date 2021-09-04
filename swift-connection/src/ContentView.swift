//
//  ContentView.swift
//  ezyfox-ssl
//
//  Created by Dzung on 31/05/2021.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        SocketProxy.getInstance().connectToServer();
        return Text("Hello, world!")
            .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
