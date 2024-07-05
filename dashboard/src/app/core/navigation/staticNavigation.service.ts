import { Injectable } from '@angular/core';
import { cloneDeep } from 'lodash-es';
import { FuseNavigationItem } from '@fuse/components/navigation';
import {
    compactNavigation,
    defaultNavigation,
    futuristicNavigation,
    horizontalNavigation,
    publisherNavigation,
    userNavigation,
} from 'app/core/navigation/data';

@Injectable({
    providedIn: 'root',
})
export class StaticNavigation {
    private readonly _compactNavigation: FuseNavigationItem[] =
        compactNavigation;
    private readonly _defaultNavigation: FuseNavigationItem[] =
        defaultNavigation;
    private readonly _futuristicNavigation: FuseNavigationItem[] =
        futuristicNavigation;
    private readonly _horizontalNavigation: FuseNavigationItem[] =
        horizontalNavigation;
    private readonly _userNavigation: FuseNavigationItem[] = userNavigation;
    private readonly _publisherNavigation: FuseNavigationItem[] =
        publisherNavigation;

    /**
     * Constructor
     */
    constructor() {
        // Register Mock API handlers
        this.registerHandlers();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Register Mock API handlers
     */
    registerHandlers() {
        // Fill compact navigation children using the default navigation
        this._compactNavigation.forEach((compactNavItem) => {
            this._defaultNavigation.forEach((defaultNavItem) => {
                if (defaultNavItem.id === compactNavItem.id) {
                    compactNavItem.children = cloneDeep(
                        defaultNavItem.children
                    );
                }
            });
        });

        // Fill futuristic navigation children using the default navigation
        this._futuristicNavigation.forEach((futuristicNavItem) => {
            this._defaultNavigation.forEach((defaultNavItem) => {
                if (defaultNavItem.id === futuristicNavItem.id) {
                    futuristicNavItem.children = cloneDeep(
                        defaultNavItem.children
                    );
                }
            });
        });

        // Fill horizontal navigation children using the default navigation
        this._horizontalNavigation.forEach((horizontalNavItem) => {
            this._defaultNavigation.forEach((defaultNavItem) => {
                if (defaultNavItem.id === horizontalNavItem.id) {
                    horizontalNavItem.children = cloneDeep(
                        defaultNavItem.children
                    );
                }
            });
        });

        // Fill horizontal navigation children using the default navigation
        this._horizontalNavigation.forEach((horizontalNavItem) => {
            this._userNavigation.forEach((defaultNavItem) => {
                if (defaultNavItem.id === horizontalNavItem.id) {
                    horizontalNavItem.children = cloneDeep(
                        defaultNavItem.children
                    );
                }
            });
        });
        // Fill horizontal navigation children using the default navigation
        this._horizontalNavigation.forEach((horizontalNavItem) => {
            this._publisherNavigation.forEach((defaultNavItem) => {
                if (defaultNavItem.id === horizontalNavItem.id) {
                    horizontalNavItem.children = cloneDeep(
                        defaultNavItem.children
                    );
                }
            });
        });

        // Fill compact navigation children using the default navigation
        this._compactNavigation.forEach((compactNavItem) => {
            this._userNavigation.forEach((defaultNavItem) => {
                if (defaultNavItem.id === compactNavItem.id) {
                    compactNavItem.children = cloneDeep(
                        defaultNavItem.children
                    );
                }
            });
        });

        // Fill futuristic navigation children using the default navigation
        this._futuristicNavigation.forEach((futuristicNavItem) => {
            this._userNavigation.forEach((defaultNavItem) => {
                if (defaultNavItem.id === futuristicNavItem.id) {
                    futuristicNavItem.children = cloneDeep(
                        defaultNavItem.children
                    );
                }
            });
        });

        // Fill compact navigation children using the default navigation
        this._compactNavigation.forEach((compactNavItem) => {
            this._publisherNavigation.forEach((defaultNavItem) => {
                if (defaultNavItem.id === compactNavItem.id) {
                    compactNavItem.children = cloneDeep(
                        defaultNavItem.children
                    );
                }
            });
        });

        // Fill futuristic navigation children using the default navigation
        this._futuristicNavigation.forEach((futuristicNavItem) => {
            this._publisherNavigation.forEach((defaultNavItem) => {
                if (defaultNavItem.id === futuristicNavItem.id) {
                    futuristicNavItem.children = cloneDeep(
                        defaultNavItem.children
                    );
                }
            });
        });
        // Return the response
        return {
            compact: cloneDeep(this._compactNavigation),
            default: cloneDeep(this._defaultNavigation),
            futuristic: cloneDeep(this._futuristicNavigation),
            horizontal: cloneDeep(this._horizontalNavigation),
            user: cloneDeep(this._userNavigation),
            publisher: cloneDeep(this._publisherNavigation),
        }
    }
}
