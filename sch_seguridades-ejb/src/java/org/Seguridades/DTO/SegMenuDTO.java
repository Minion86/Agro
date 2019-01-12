/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Seguridades.DTO;

import java.util.ArrayList;
import java.util.List;
import org.Seguridades.Entities.SegMenu;

/**
 *
 * @author fmullo
 */
public class SegMenuDTO {

    private SegMenu segMenu;
    private List<SegAccionesDTO> segAccionesDTOlist = new ArrayList<>();

    public SegMenuDTO(SegMenu segMenu, List<SegAccionesDTO> segAccionesDTOlist) {
        this.segMenu = segMenu;
        this.segAccionesDTOlist = segAccionesDTOlist;
    }

    /**
     * @return the segMenu
     */
    public SegMenu getSegMenu() {
        return segMenu;
    }

    /**
     * @param segMenu the segMenu to set
     */
    public void setSegMenu(SegMenu segMenu) {
        this.segMenu = segMenu;
    }

    /**
     * @return the segAccionesDTOlist
     */
    public List<SegAccionesDTO> getSegAccionesDTOlist() {
        return segAccionesDTOlist;
    }

    /**
     * @param segAccionesDTOlist the segAccionesDTOlist to set
     */
    public void setSegAccionesDTOlist(List<SegAccionesDTO> segAccionesDTOlist) {
        this.segAccionesDTOlist = segAccionesDTOlist;
    }

}
