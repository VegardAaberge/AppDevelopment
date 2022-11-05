//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Vegard Aaberge on 2022/11/2.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteListScreen {
    @MainActor class NoteListViewModel: ObservableObject {
        private var noteDateSource: NoteDataSource? = nil
        
        private let searchNotes = SearchNotes()
        
        private var notes = [Note]()
        @Published private(set) var filterNotes = [Note]()
        @Published var searchText = ""{
            didSet {
                self.filterNotes = searchNotes.execute(notes: self.notes, query: searchText)
            }
        }
        @Published private(set) var isSearchActive = false
        
        init(noteDateSource: NoteDataSource? = nil){
            self.noteDateSource = noteDateSource
        }
        
        func loadNotes(){
            noteDateSource?.getAllNotes(completionHandler: { notes, error in
                self.notes = notes ?? []
                self.filterNotes = self.searchNotes.execute(notes: self.notes, query: self.searchText)
            })
        }
        
        func deleteNoteById(id: Int64?){
            if(id != nil){
                noteDateSource?.deleteNoteById(id: id!, completionHandler: { error in
                    self.loadNotes()
                })
            }
        }
        
        func toggleIsSearchActive() {
            isSearchActive = !isSearchActive
            if(isSearchActive){
                searchText = ""
            }
        }
        
        func setNoteDataSource(noteDateSource: NoteDataSource){
            self.noteDateSource = noteDateSource
        }
    }
}
