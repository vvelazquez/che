/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.ext.git.client.compare.changedList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.ide.ext.git.client.GitLocalizationConstant;
import org.eclipse.che.ide.ext.git.client.tree.TreeView;
import org.eclipse.che.ide.ui.window.Window;

/**
 * Implementation of {@link ChangedListView}.
 *
 * @author Igor Vinokur
 */
@Singleton
public class ChangedListViewImpl extends Window implements ChangedListView {

    private final GitLocalizationConstant locale;

    private ActionDelegate delegate;
    private Button         btnCompare;

    @Inject
    protected ChangedListViewImpl(TreeView treeView,
                                  GitLocalizationConstant locale) {
        this.locale = locale;

        FlowPanel widget = new FlowPanel();

        this.setTitle(locale.changeListTitle());
        widget.add(treeView);
        this.setWidget(widget);

        createButtons();

        SafeHtmlBuilder shb = new SafeHtmlBuilder();

        shb.appendHtmlConstant("<table height =\"20\">");
        shb.appendHtmlConstant("<tr height =\"3\"></tr><tr>");
        shb.appendHtmlConstant("<td width =\"20\" bgcolor =\"dodgerBlue\"></td>");
        shb.appendHtmlConstant("<td>modified</td>");
        shb.appendHtmlConstant("<td width =\"20\" bgcolor =\"red\"></td>");
        shb.appendHtmlConstant("<td>deleted</td>");
        shb.appendHtmlConstant("<td width =\"20\" bgcolor =\"green\"></td>");
        shb.appendHtmlConstant("<td>added</td>");
        shb.appendHtmlConstant("<td width =\"20\" bgcolor =\"purple\"></td>");
        shb.appendHtmlConstant("<td>copied</td>");
        shb.appendHtmlConstant("</tr></table>");

        getFooter().add(new HTML(shb.toSafeHtml()));
    }

    @Override
    public void setEnableCompareButton(boolean enableCompareButton) {
        btnCompare.setEnabled(enableCompareButton);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        this.hide();
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog() {
        this.show();
    }

    private void createButtons() {
        Button btnClose = createButton(locale.buttonClose(), "git-compare-btn-close", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCloseClicked();
            }
        });
        addButtonToFooter(btnClose);

        btnCompare = createButton(locale.buttonCompare(), "git-compare-btn-compare", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCompareClicked();
            }
        });
        addButtonToFooter(btnCompare);
    }
}
